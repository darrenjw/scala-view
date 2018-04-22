/*
sphere.scala

 *** NOT FINISHED WORK IN PROGRESS!!! ***

 */


import scalafx.scene.image.WritableImage
import scalafx.scene.canvas.Canvas
import scalafx.scene.paint._
import javafx.embed.swing.{JFXPanel,SwingFXUtils}
import java.awt.image.BufferedImage
import java.awt.Graphics2D

object Sphere {

  case class Vertex(x: Double, y: Double, z: Double) {

    import math._

    def normalise: Vertex = {
      val l2 = x*x + y*y + z*z
      val l = sqrt(l2)
      Vertex(x/l, y/l, z/l)
    }

    def rotate(th: Double) = Vertex(x*cos(th) - z*sin(th), y, x*sin(th) + z*cos(th))

  }

  case class Triangle(v1: Vertex, v2: Vertex, v3: Vertex) {

    def rotate(th: Double) = Triangle(v1.rotate(th),v2.rotate(th),v3.rotate(th))

  }


  // vertices of an icosahedron
  val g = 0.5*(1.0+math.sqrt(5.0))
  val iv = List(
    Vertex(g,1,0), Vertex(-g,1,0), Vertex(g,-1,0), Vertex(-g,-1,0),
    Vertex(0,g,1), Vertex(0,-g,1), Vertex(0,g,-1), Vertex(0,-g,-1),
    Vertex(1,0,g), Vertex(1,0,-g), Vertex(-1,0,g), Vertex(-1,0,-g)
  )
  val ivn = iv map (_.normalise)

  // faces of an icosahedron
  val ifac = List(
    Triangle(ivn(0),ivn(2),ivn(8)),
    Triangle(ivn(0),ivn(8),ivn(4)),
    Triangle(ivn(0),ivn(4),ivn(6)),
    Triangle(ivn(0),ivn(6),ivn(9)),
    Triangle(ivn(0),ivn(9),ivn(2)),
    Triangle(ivn(3),ivn(1),ivn(11)),
    Triangle(ivn(3),ivn(11),ivn(7)),
    Triangle(ivn(3),ivn(7),ivn(5)),
    Triangle(ivn(3),ivn(5),ivn(10)),
    Triangle(ivn(3),ivn(10),ivn(1)) // and the rest!
  )


  def vl2i(vl: Seq[Vertex],s: Int): WritableImage = {
    val bi = new BufferedImage(s,s,BufferedImage.TYPE_INT_ARGB)
    val g = bi.createGraphics()
    def sc(x: Double): Int = (0.5*s*(x + 1.0)).toInt
    g.clearRect(0,0,s,s)
    vl map (v => g.fillOval(sc(v.x),sc(v.y),10,10))
    new WritableImage(SwingFXUtils.toFXImage(bi,null))
  }

  def tl2i(tl: Seq[Triangle],s: Int): WritableImage = {
    val bi = new BufferedImage(s,s,BufferedImage.TYPE_INT_ARGB)
    val g = bi.createGraphics()
    def sc(x: Double): Int = (0.5*s*(x + 1.0)).toInt
    g.clearRect(0,0,s,s)
    tl map (t => {
      val xs = Array(t.v1.x, t.v2.x, t.v3.x)
      val ys = Array(t.v1.y, t.v2.y, t.v3.y)
      val sxs = xs map sc
      val sys = ys map sc
      g.drawPolygon(sxs,sys,3)
    })
    new WritableImage(SwingFXUtils.toFXImage(bi,null))
  }


  def main(args: Array[String]): Unit = {
    new JFXPanel() // Initialise FX thread
    println("hi")
    println(iv)
    println(ivn)

    (0 until 12).foreach(i => {
      (i+1 until 12).foreach(j => {
        val d2 = (iv(i).x-iv(j).x)*(iv(i).x-iv(j).x) + (iv(i).y-iv(j).y)*(iv(i).y-iv(j).y) + (iv(i).z-iv(j).z)*(iv(i).z-iv(j).z) 
        if (d2<5) print(s"($i,$j,$d2) ")
      })
      println()
    })

    def ivs = Stream.iterate(ivn)(vl => vl map (_.rotate(0.01)))
    def ivsi = ivs map (vl2i(_,1000))
    //scalaview.SfxImageViewer(ivsi,100000000)

    def ifs = Stream.iterate(ifac)(fl => fl map (_.rotate(0.01)))
    def ifsi = ifs map (tl2i(_,1000))
    scalaview.SfxImageViewer(ifsi,10000000)

    println("bye")
  }

}

// eof

