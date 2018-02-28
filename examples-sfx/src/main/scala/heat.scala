import scalafx.scene.image.WritableImage
import scalafx.scene.paint._

object HeatEquation {

  def kernel(pi: PImage[Double]): Double = (2*pi.extract+
  pi.up.extract+pi.down.extract+pi.left.extract+pi.right.extract)/6.0

  def toSfxI(im: Image[Double]): WritableImage = {
    val wi = new WritableImage(im.w, im.h)
    val pw = wi.pixelWriter
    (0 until im.w) foreach (i =>
      (0 until im.h) foreach (j =>
        pw.setColor(i, j, Color.gray(im(i,j)))
      ))
    wi
  }

  def main(args: Array[String]): Unit = {
    val w = 600
    val h = 500
    val beta = 0.45

    val pim0 = PImage(0, 0, Image(w, h, Vector.fill(w * h)(math.random).par))

    def pims = Stream.iterate(pim0)(_.coflatMap(kernel))
    def sfxis = pims map (im => toSfxI(im.image))
    scalaview.SfxImageViewer(sfxis,1e8.toInt)
  }

}
