import scalafx.scene.image.WritableImage
import scalafx.scene.paint._

object IsingModel {

  def gibbsKernel(beta: Double)(pi: PImage[Int]): Int = {
    val sum = pi.up.extract + pi.down.extract + pi.left.extract + pi.right.extract
    val p1 = math.exp(beta * sum)
    val p2 = math.exp(-beta * sum)
    val probplus = p1 / (p1 + p2)
    if (math.random < probplus) 1 else -1
  }

  def oddKernel(beta: Double)(pi: PImage[Int]): Int =
    if ((pi.x + pi.y) % 2 != 0) pi.extract else gibbsKernel(beta)(pi)
  def evenKernel(beta: Double)(pi: PImage[Int]): Int =
    if ((pi.x + pi.y) % 2 == 0) pi.extract else gibbsKernel(beta)(pi)

  def toSfxI(im: Image[Int]): WritableImage = {
    val wi = new WritableImage(im.w, im.h)
    val pw = wi.pixelWriter
    (0 until im.w) foreach (i =>
      (0 until im.h) foreach (j =>
        pw.setColor(i, j, Color.gray(if (im(i, j) == 1) 1 else 0))))
    wi
  }

  def main(args: Array[String]): Unit = {
    val w = 600
    val h = 500
    val beta = 0.45

    val pim0 = PImage(0, 0, Image(w, h, Vector.fill(w * h)(if (math.random > 0.5) 1 else -1).par))
    def pims = Stream.iterate(pim0)(_.coflatMap(oddKernel(beta)).coflatMap(evenKernel(beta)))
    def sfxis = pims map (im => toSfxI(im.image))
    //scalaview.SfxImageViewer(sfxis)
    scalaview.SfxImageViewer(sfxis, autoStart = true)
    //val fis = sfxis.take(100)
    //scalaview.SfxImageViewer(fis, autoStart = true)
    //scalaview.SfxImageViewer(sfxis.drop(2).head)
  }

}
