/*
sphere.scala

Mesh a sphere rotate

 */

import scalafx.scene.image.WritableImage
import scalafx.scene.canvas.Canvas
import javafx.embed.swing.{JFXPanel,SwingFXUtils}
import java.awt.image.BufferedImage
import java.awt.{Graphics2D,Color}
import scala.collection.GenSeq
import scala.collection.parallel.immutable.ParVector

object Sphere {

  case class Vertex(x: Double, y: Double, z: Double) {

    import math._

    def normalise: Vertex = {
      val l2 = x*x + y*y + z*z
      val l = sqrt(l2)
      Vertex(x/l, y/l, z/l)
    }

    def rotate(th: Double) = Vertex(x*cos(th) - z*sin(th), y,
      x*sin(th) + z*cos(th))

    def midpoint(v: Vertex): Vertex = {
      val mx = 0.5*(x+v.x)
      val my = 0.5*(y+v.y)
      val mz = 0.5*(z+v.z)
      Vertex(mx,my,mz)
    }

  }

  case class Triangle(v1: Vertex, v2: Vertex, v3: Vertex) {

    def rotate(th: Double) = Triangle(v1.rotate(th),v2.rotate(th),v3.rotate(th))

    def normalise: Triangle = Triangle(v1.normalise,v2.normalise,v3.normalise)

    def centroid: Vertex = {
      val cx = (v1.x+v2.x+v3.x)/3.0
      val cy = (v1.y+v2.y+v3.y)/3.0
      val cz = (v1.z+v2.z+v3.z)/3.0
      Vertex(cx,cy,cz)
    }

    def subdivide: Vector[Triangle] = {
      val v12 = v1.midpoint(v2)
      val v23 = v2.midpoint(v3)
      val v13 = v1.midpoint(v3)
      Vector(
        Triangle(v1,v12,v13),
        Triangle(v12,v23,v13),
        Triangle(v13,v23,v3),
        Triangle(v12,v2,v23)
      )
    }

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
  val ifac = Vector(
    Triangle(ivn(0),ivn(2),ivn(8)), // triangles from 0
    Triangle(ivn(0),ivn(8),ivn(4)),
    Triangle(ivn(0),ivn(4),ivn(6)),
    Triangle(ivn(0),ivn(6),ivn(9)),
    Triangle(ivn(0),ivn(9),ivn(2)),
    Triangle(ivn(3),ivn(1),ivn(11)), // triangles from 3
    Triangle(ivn(3),ivn(11),ivn(7)),
    Triangle(ivn(3),ivn(7),ivn(5)),
    Triangle(ivn(3),ivn(5),ivn(10)),
    Triangle(ivn(3),ivn(10),ivn(1)),
    Triangle(ivn(1),ivn(4),ivn(6)), // connecting triangles from 3 layer
    Triangle(ivn(5),ivn(2),ivn(8)),
    Triangle(ivn(7),ivn(2),ivn(9)),
    Triangle(ivn(10),ivn(8),ivn(4)),
    Triangle(ivn(11),ivn(9),ivn(6)),
    Triangle(ivn(2),ivn(5),ivn(7)), // connecting triangles from 0 layer
    Triangle(ivn(4),ivn(1),ivn(10)),
    Triangle(ivn(6),ivn(1),ivn(11)),
    Triangle(ivn(8),ivn(10),ivn(5)),
    Triangle(ivn(9),ivn(11),ivn(7))
  ).par


  // Vertex list to image
  def vl2i(vl: GenSeq[Vertex], s: Int): WritableImage = {
    val bi = new BufferedImage(s,s,BufferedImage.TYPE_INT_ARGB)
    val g = bi.createGraphics()
    def sc(x: Double): Int = (0.5*s*(x + 1.0)).toInt
    g.clearRect(0,0,s,s)
    vl map (v => g.fillOval(sc(v.x),sc(v.y),10,10))
    new WritableImage(SwingFXUtils.toFXImage(bi,null))
  }

  // Triangle list to image
  def tl2i(tl: ParVector[Triangle], s: Int): WritableImage = {
    val bi = new BufferedImage(s,s,BufferedImage.TYPE_INT_ARGB)
    val g = bi.createGraphics()
    def sc(x: Double): Int = (0.5*s*(x + 1.0)).toInt
    g.clearRect(0,0,s,s)
    val sl = tl.toVector.sortBy(_.centroid.z)
    sl map (t => {
      val xs = Array(t.v1.x, t.v2.x, t.v3.x)
      val ys = Array(t.v1.y, t.v2.y, t.v3.y)
      val sxs = xs map sc
      val sys = ys map sc
      g.setColor(new Color(100,100,100,200))
      g.fillPolygon(sxs,sys,3)
      g.setColor(new Color(255,255,255,255))
      g.drawPolygon(sxs,sys,3)
    })
    new WritableImage(SwingFXUtils.toFXImage(bi,null))
  }


  def main(args: Array[String]): Unit = {
    val sf1 = ifac flatMap (_.subdivide) map (_.normalise)
    val sf2 = sf1 flatMap (_.subdivide) map (_.normalise)
    val sf3 = sf2 flatMap (_.subdivide) map (_.normalise)
    def ifs = Stream.iterate(sf3)(fl => fl map (_.rotate(0.02)))
    def ifsi = ifs map (tl2i(_, 1000))
    scalaview.SfxImageViewer(ifsi, autoStart = true)
  }

}

// eof

