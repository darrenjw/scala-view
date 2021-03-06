/*
star.scala

Time-evolution of a STAR(1) model with censoring/thresholding
Potentially useful as a spatio-temporal prior distribution for rainfall

*/


import scalafx.scene.image.WritableImage
import scalafx.scene.paint._

object Star {

  val rng = new scala.util.Random

  def kernel(alpha: Double, beta: Double, sig: Double, wind: Double,direction: Double)(pi: PImage[Double]): Double = {
    val av = (1.0-4.0*beta)*pi.extract + beta*(pi.up.extract + pi.down.extract + pi.left.extract + pi.right.extract)
    val drift = wind*( math.cos(direction)*(pi.right.extract-pi.left.extract) +  math.sin(direction)*(pi.up.extract-pi.down.extract))
    alpha*av + drift + sig*rng.nextGaussian
  }

  def toSfxI(thresh: Double, im: Image[Double]): WritableImage = {
    val wi = new WritableImage(im.w, im.h)
    val pw = wi.pixelWriter
    val mx = im.reduce(math.max)
    val mn = im.reduce(math.min)
    //println(mn,mx)
    (0 until im.w).par foreach (i =>
      (0 until im.h) foreach (j => {
        val h = math.max(0.0, im(i,j) - thresh)
        val sh = h / (mx-thresh)
        pw.setColor(i, j, if (h == 0.0) Color.rgb(135,206,250) else Color.gray(1.0 - sh))
      }
      ))
    wi
  }

  def main(args: Array[String]): Unit = {
    val w = 500
    val h = 400
    val alpha = 0.99999 // auto-regressive parameter - should be < 1
    val beta = 0.2 // diffusion rate - should be <= 0.2
    val sig = 0.001 // Noise - quite small
    val wind = 0.15 // Magnitude of wind/drift (should be < beta)
    val direction = 0.75*math.Pi // Wind direction, in radians, anti-clock from Easterly
    val thresh = 0.01 // threshold for censoring/truncation
    val pim0 = PImage(0, 0, Image(w, h, Vector.fill(w * h)(rng.nextGaussian).par))
    def pims = Stream.iterate(pim0)(_.coflatMap(kernel(alpha,beta,sig,wind,direction)))
    def sfxis = pims.
      map(im => toSfxI(thresh, im.image)).
      map(im => scalaview.Utils.iResize(im,1500,1200))
    scalaview.SfxImageViewer(sfxis, autoStart = true)
  }

}

/* eof */

