/*
gmrf.scala

Visualising iterations of a Gibbs sampler for sampling a GMRF

 */


import scalafx.scene.image.WritableImage
import scalafx.scene.paint._

object Gmrf {

  val rng = new scala.util.Random

  def gibbsKernel(beta: Double)(pi: PImage[Double]): Double = {
    val av = (pi.up.extract + pi.down.extract + pi.left.extract + pi.right.extract)/4.0
    av + beta*(rng.nextGaussian)
  }

  def oddKernel(beta: Double)(pi: PImage[Double]): Double =
    if ((pi.x + pi.y) % 2 != 0) pi.extract else gibbsKernel(beta)(pi)
  def evenKernel(beta: Double)(pi: PImage[Double]): Double =
    if ((pi.x + pi.y) % 2 == 0) pi.extract else gibbsKernel(beta)(pi)

  def toSfxI(im: Image[Double]): WritableImage = {
    val wi = new WritableImage(im.w, im.h)
    val pw = wi.pixelWriter
    val mx = im.reduce(math.max)
    val mn = im.reduce(math.min)
    (0 until im.w).par foreach (i =>
      (0 until im.h) foreach (j =>
        pw.setColor(i, j, Color.gray((im(i, j)-mn)/(mx-mn)))
      ))
    wi
  }

  def main(args: Array[String]): Unit = {
    val w = 600
    val h = 500
    val beta = 2.0

    val pim0 = PImage(0, 0, Image(w, h, Vector.fill(w * h)(math.random).par))
    def pims = Stream.iterate(pim0)(_.coflatMap(oddKernel(beta)).coflatMap(evenKernel(beta)))
    def sfxis = pims map (im => toSfxI(im.image))
    scalaview.SfxImageViewer(sfxis, autoStart = true)
  }

}

/* eof */

