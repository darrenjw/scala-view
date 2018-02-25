/*
sierpinski.scala

sierpinski carpet construction

 */

import java.awt.image.BufferedImage
import java.awt.{Color, Graphics}
import scalaview.SwingImageViewer

object Sierpinski {

  def drawSierpinski(g: Graphics, x: Double, y: Double, w: Double, h: Double, l: Int): Unit = {
    if (l == 0)
      g.fillRect(x.round.toInt, y.round.toInt, w.round.toInt, h.round.toInt)
    else {
      drawSierpinski(g, x, y, w / 3, h / 3, l - 1)
      drawSierpinski(g, x + w / 3, y, w / 3, h / 3, l - 1)
      drawSierpinski(g, x + (2 * w) / 3, y, w / 3, h / 3, l - 1)
      drawSierpinski(g, x, y + h / 3, w / 3, h / 3, l - 1)
      drawSierpinski(g, x + (2 * w) / 3, y + h / 3, w / 3, h / 3, l - 1)
      drawSierpinski(g, x, y + (2 * h) / 3, w / 3, h / 3, l - 1)
      drawSierpinski(g, x + w / 3, y + (2 * h) / 3, w / 3, h / 3, l - 1)
      drawSierpinski(g, x + (2 * w) / 3, y + (2 * h) / 3, w / 3, h / 3, l - 1)
    }
  }

  def makeSierpinski(width: Int = 500, height: Int = 500, level: Int = 3): BufferedImage = {
    val canvas = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)
    val g = canvas.getGraphics
    g.setColor(Color.white)
    g.fillRect(0, 0, width, height)
    g.setColor(Color.black)
    drawSierpinski(g, 0.0, 0.0, width.toDouble, height.toDouble, level)
    canvas
  }

  def main(args: Array[String]): Unit = {
    println("Hi")
    val is = (0 to 6).toStream.map(i => makeSierpinski(729, 729, i))
    SwingImageViewer(is, 2000, autoStart = true)
    println("Bye")
  }

}

// eof

