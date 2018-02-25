/*
ising.scala

 */

import scalaview.SwingImageViewer
import scalaview.Utils._

object Ising {

  import breeze.linalg._

  val size = 1000
  val beta = 0.43
  def rng = java.util.concurrent.ThreadLocalRandom.current()
  val ind = (0 until size).toVector
  val oind = (1 until size by 2).toVector
  val eind = (0 until size by 2).toVector
  val indP = ind.par
  val oindP = oind.par
  val eindP = eind.par

  def neighbours(state: DenseMatrix[Int], i: Int, j: Int): Int = {
    ((if (i > 0) state(i - 1, j) else 0)
      + (if (i < size - 1) state(i + 1, j) else 0)
      + (if (j > 0) state(i, j - 1) else 0)
      + (if (j < size - 1) state(i, j + 1) else 0))
  }

  def nextState(state: DenseMatrix[Int]): DenseMatrix[Int] = {
    val ns = state.copy
    // first even columns (in parallel)
    eindP.foreach { i =>
      ind.foreach { j =>
        val pp = math.exp(beta * neighbours(ns, i, j))
        val pm = math.exp(-beta * neighbours(ns, i, j))
        ns(i, j) = if (rng.nextDouble(1.0) < pp / (pp + pm)) +1 else -1
      }
    }
    // now odd columns (in parallel)
    oindP.foreach { i =>
      ind.foreach { j =>
        val pp = math.exp(beta * neighbours(ns, i, j))
        val pm = math.exp(-beta * neighbours(ns, i, j))
        ns(i, j) = if (rng.nextDouble(1.0) < pp / (pp + pm)) +1 else -1
      }
    }
    ns
  }

  def stateStream(s: DenseMatrix[Int]): Stream[DenseMatrix[Int]] = Stream.iterate(s)(nextState(_))

  import java.awt.image.BufferedImage
  def state2Bi(s: DenseMatrix[Int]): BufferedImage = {
    val canvas = new BufferedImage(s.cols, s.rows, BufferedImage.TYPE_BYTE_BINARY)
    val wr = canvas.getRaster
    // write columns in parallel
    indP.foreach { x =>
      ind.foreach { y =>
        wr.setSample(x, y, 0, if (s(x, y) == 1) 1 else 0)
      }
    }
    canvas
  }

  def main(args: Array[String]): Unit = {
    println("Hi")
    val initState = DenseMatrix.tabulate(size, size) {
      case (i, j) => if (rng.nextDouble(1.0) < 0.5) 1 else -1
    }
    val is = thinStream(stateStream(initState),5).
      map(s => state2Bi(s))
    SwingImageViewer(is, autoStart = true)
  }

}

/* eof */

