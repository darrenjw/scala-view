/*
julia.scala

Julia set animation

 */

import breeze.math.Complex
import java.awt.image.BufferedImage
import scalaview.SwingImageViewer

object JuliaSet {

  @annotation.tailrec
  def inSet(z: Complex, c: Complex, maxIter: Int = 256, iter: Int = 0): Int = {
    if (iter >= maxIter) 0 else {
      val zz = z * z + c
      if (zz.abs > 1000.0) (iter % 2) else
        inSet(zz, c, maxIter, iter + 1)
    }
  }

  def setImage(c: Complex, pixels: Int = 800, z: Complex = Complex(-2.0, -2.0), size: Double = 4.0): BufferedImage = {
    val bi = new BufferedImage(pixels, pixels, BufferedImage.TYPE_BYTE_BINARY)
    val wr = bi.getRaster
    val is = (0 until pixels).toVector.par // parallelise over columns
    val js = (0 until pixels).toVector // don't parallelise over rows
    is.foreach { i =>
      js.foreach { j =>
        val zz = Complex(z.re + (size * i) / pixels, z.im + (size * j) / pixels)
        wr.setSample(i, j, 0, inSet(zz, c))
      }
    }
    bi
  }

  def main(args: Array[String]): Unit = {
    println("hi")
    val numFrames = 1000
    val i = (1 to numFrames).toStream
    val is = i.map(i => setImage(Complex(-2.0 + (3.0 * i) / numFrames, -0.5 + (2.0 * i) / numFrames)))
    SwingImageViewer(is)
    println("bye")
  }

}

// eof

