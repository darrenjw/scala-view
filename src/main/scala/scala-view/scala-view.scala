/*
scala-view.scala
Visualise an image stream with a Swing frame

Copyright (C) 2016 Darren J Wilkinson
https://github.com/darrenjw

 */

package scalaview

import scala.swing._
import java.awt.{Graphics2D, Color, BasicStroke}
import java.awt.image.BufferedImage
import scala.util.Random
import scala.swing.event.{ButtonClicked, WindowClosing}

/**
 * Class having the side-effect of rendering a Stream of BufferedImages in a JFrame on-screen.
 * Better to use the constructor(s) in the companion object.
 *
 * @constructor Render the stream of images on-screen
 * @param is Stream of BufferedImages to be animated on-screen
 * @param timerDelay Delay, in milliseconds, between rendering of frames in the stream
 */
class SwingImageViewer(var is: Stream[BufferedImage], timerDelay: Int) {

  def top = new MainFrame {
    title = "Swing Image Viewer"
    val start = new Button { text = "Start" }
    val stop = new Button { text = "Stop" }
    val panel = ImagePanel(is.head.getWidth, is.head.getHeight)
    contents = new BoxPanel(Orientation.Vertical) {
      contents += start
      contents += stop
      contents += panel
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }
    peer.setDefaultCloseOperation(0)
    listenTo(start)
    listenTo(stop)
    val timer = new javax.swing.Timer(timerDelay, Swing.ActionListener(e => {
      if (!is.isEmpty) {
        panel.bi = is.head
        is = is.tail
        panel.repaint()
      }
    }))
    reactions += {
      case ButtonClicked(b) => {
        if (b.text == "Start")
          timer.start()
        else
          timer.stop()
      }
      case WindowClosing(_) => {
        println("Close button clicked. Exiting...")
        sys.exit()
      }
    }
  }

}

object SwingImageViewer {

  /**
   * Constructor to be used for rendering image streams
   *
   * @constructor Render the provided image stream on-screen
   * @param is Stream of images to be rendered on-screen
   * @param timerDelay Delay, in milliseconds, between frames of the image to be rendered on screen. Use the default for streams to be rendered as soon as each image is computed.
   * @return In principle this returns an object, but in practice it is called purely for the side-effect of on-screen rendering.
   */
  def apply(is: Stream[BufferedImage], timerDelay: Int = 1): SwingImageViewer = {
    val siv = new SwingImageViewer(is, timerDelay)
    siv.top.visible = true
    siv
  }

  /**
   * Constructor to be used for rendering a single image
   *
   * @constructor Render the provided image on-screen
   * @param im is the image to be rendered
   * @return In principle this returns an object, but in practice it is called purely for the side-effect of on-screen rendering.
   */
  def apply(im: BufferedImage): SwingImageViewer = {
    apply(Stream(im))
  }

}

case class ImagePanel(var bi: BufferedImage) extends Panel {
  override def paintComponent(g: Graphics2D) = {
    g.drawImage(bi, 0, 0, null)
  }
}

object ImagePanel {
  def apply(x: Int, y: Int) = {
    val bi = new BufferedImage(x, y, BufferedImage.TYPE_BYTE_BINARY)
    val ip = new ImagePanel(bi)
    ip.preferredSize = new Dimension(x, y)
    ip
  }
}

/* eof */

