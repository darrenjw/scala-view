/*
heat.scala

Visualising a finite difference solution of the 2d heat equation
Equivalently, Gaussian blurring of an image

Loosely based on the example from:

https://darrenjw.wordpress.com/2018/01/22/comonads-for-scientific-and-statistical-computing-in-scala/

 */

import scalafx.scene.image.WritableImage
import scalafx.scene.paint._

object HeatEquation {

  def kernel(pi: PImage[Double]): Double = (2*pi.extract+
  pi.up.extract+pi.down.extract+pi.left.extract+pi.right.extract)/6.0

  def toSfxI(im: Image[Double]): WritableImage = {
    val wi = new WritableImage(im.w, im.h)
    val pw = wi.pixelWriter
    (0 until im.w).par foreach (i =>
      (0 until im.h) foreach (j =>
        pw.setColor(i, j, Color.gray(im(i,j)))
      ))
    wi
  }

  def main(args: Array[String]): Unit = {
    val w = 600
    val h = 500

    val pim0 = PImage(0, 0, Image(w, h,
      ((0 until w*h).toVector map {i: Int => {
        val x = i / h
        val y = i % h
        0.1*math.cos(0.1*math.sqrt((x*x+y*y))) + 0.1 + 0.8*math.random
      }}).par
    ))

    def pims = Stream.iterate(pim0)(_.coflatMap(kernel))
    def sfxis = pims map (im => toSfxI(im.image))
    scalaview.SfxImageViewer(sfxis,1e7.toInt)
  }

}

/* eof */
