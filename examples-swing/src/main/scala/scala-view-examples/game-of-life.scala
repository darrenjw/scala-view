/*
game-of-life.scala

 */

import scalaview.SwingImageViewer
import scalaview.Utils._

object GameOfLife {

  import breeze.linalg._

  val size = 100

  def neighbours(state: DenseMatrix[Int], i: Int, j: Int): Int = {
    ((if (i > 0) state(i - 1, j) else 0)
      + (if (i < size - 1) state(i + 1, j) else 0)
      + (if (j > 0) state(i, j - 1) else 0)
      + (if (j < size - 1) state(i, j + 1) else 0)
      + (if ((i < size - 1) & (j < size - 1)) state(i + 1, j + 1) else 0)
      + (if ((i < size - 1) & (j > 0)) state(i + 1, j - 1) else 0)
      + (if ((i > 0) & (j < size - 1)) state(i - 1, j + 1) else 0)
      + (if ((i > 0) & (j > 0)) state(i - 1, j - 1) else 0))
  }

  def neighbourMatrix(state: DenseMatrix[Int]): DenseMatrix[Int] =
    DenseMatrix.tabulate(size, size) { case (i, j) => neighbours(state, i, j) }

  def nextState(state: DenseMatrix[Int]): DenseMatrix[Int] = {
    val neighMat = neighbourMatrix(state)
    DenseMatrix.tabulate(size, size) {
      case (i, j) =>
        if ((neighMat(i, j) < 2) | (neighMat(i, j) > 3)) 0
        else if (neighMat(i, j) == 3) 1
        else state(i, j)
    }
  }

  def stateStream(s: DenseMatrix[Int]): Stream[DenseMatrix[Int]] = Stream.iterate(s)(nextState(_))

  import java.awt.image.BufferedImage
  def state2Bi(s: DenseMatrix[Int]): BufferedImage = {
    val canvas = new BufferedImage(s.cols, s.rows, BufferedImage.TYPE_BYTE_BINARY)
    val wr = canvas.getRaster
    for (x <- 0 until s.cols) {
      for (y <- 0 until s.rows) {
        wr.setSample(x, y, 0, 1-s(x, y))
      }
    }
    canvas
  }

  def main(args: Array[String]): Unit = {
    println("Hi")
    val initState = DenseMatrix.fill(size, size)(0)
    initState(5, 5) = 1
    initState(5, 6) = 1
    initState(5, 7) = 1
    initState(4, 7) = 1
    initState(3, 6) = 1
    val is = stateStream(initState).
      map(s => state2Bi(s)).
      map(biResize(_, size * 10, size * 10))
    SwingImageViewer(is,autoStart=true)
  }

}

/* eof */

