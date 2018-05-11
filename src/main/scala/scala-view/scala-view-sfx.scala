package scalaview

import javafx.embed.swing.{ JFXPanel, SwingFXUtils }
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scalafx.application.Platform
import scalafx.geometry.Insets
import scalafx.stage.Stage
import scalafx.scene.Scene
import scalafx.scene.layout.{ HBox, VBox }
import scalafx.scene.control.Button
import scalafx.scene.image.{ ImageView, Image }
import scalafx.scene.paint.Color

/**
  * Class having the side-effect of rendering a Stream of ScalaFX Images on-screen. Probably better to use companion object constructor in preference to the default class constructor in client code, not least because it includes useful defaults.
  *
  * @constructor Render the stream of images on-screen
  * @param is Stream of ScalaFX Images (eg. WritableImage) to be animated on-screen
  * @param timerDelay Delay, in nanoseconds, between frames of the image to be rendered on screen.
  * @param autoStart Auto-start the animation without requiring a click on the Start button?
  * @param saveFrames Save the image frames to sequentially numbered PNG files?
  * @return In principle this returns an object, but in practice it is called purely for the side-effect of on-screen rendering.
  */
class SfxImageViewer(var is: Stream[Image], timerDelay: Int, autoStart: Boolean, var saveFrames: Boolean) {

  new JFXPanel() // trick to spin up JFX

  Platform.runLater {
    val mainStage = new Stage {
      title = "ScalaFX Image Viewer"
      scene = new Scene {
        fill = Color.rgb(0, 0, 0)
        //fill = Color.rgb(255, 255, 255)
        val iv = new ImageView(is.head)
        val buttons = new HBox {
          padding = Insets(10,10,10,10)
          children = Seq(
            new Button {
              text = "Start"
              onAction = handle { timer.start }
            },
            new Button {
              text = "Stop"
              onAction = handle { timer.stop }
            },
            new Button {
              text = "Save frames"
              onAction = handle { saveFrames = true }
            },
            new Button {
              text = "Don't save frames"
              onAction = handle { saveFrames = false }
            }
          )
        }
        content = new VBox {
          padding = Insets(10,10,10,10)
          children = Seq(buttons,iv)
        }
        var lastUpdate = 0L
        var frameCounter = 0L
        val timer = AnimationTimer(t => {
          if ( (!is.isEmpty) && (t - lastUpdate > timerDelay) ) {
            iv.image = is.head
            if (saveFrames) {
              javax.imageio.ImageIO.
                write(SwingFXUtils.
                  fromFXImage(is.head,null), "png",
                  new java.io.File(f"siv-$frameCounter%06d.png"))
            }
            is = is.tail
            frameCounter += 1
            lastUpdate = t
          }
        })
        if (autoStart) timer.start
      }
    }
    mainStage.showAndWait()
  }

}

object SfxImageViewer {

  /**
    * Constructor to be used for rendering a Stream of ScalaFX Images on-screen. 
    *
    * @constructor Render the stream of images on-screen
    * @param is Stream of ScalaFX Images (eg. WritableImage) to be animated on-screen
    * @param timerDelay Delay, in nanoseconds, between frames of the image to be rendered on screen. Use the default for streams to be rendered as soon as each image is computed.
    * @param autoStart Auto-start the animation without requiring a click on the Start button (default false)?
    * @param saveFrames Save the image frames to sequentially numbered PNG files (default false)?
    * @return In principle this returns an object, but in practice it is called purely for the side-effect of on-screen rendering.
    */
  def apply(is: Stream[Image], timerDelay: Int = 1000, autoStart: Boolean = false, saveFrames: Boolean = false): SfxImageViewer =
    new SfxImageViewer(is,timerDelay,autoStart,saveFrames)

  /**
   * Constructor to be used for rendering a single image
   *
   * @constructor Render the provided image on-screen
   * @param im is the Image to be rendered
   * @return In principle this returns an object, but in practice it is called purely for the side-effect of on-screen rendering.
   */
  def apply(im: Image): SfxImageViewer = {
    apply(Stream(im), autoStart = true)
  }

}
