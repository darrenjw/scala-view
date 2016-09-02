/*
utils-tests.scala

Some basic tests for the Utils

I have no idea how to go about testing the GUI stuff...

*/

package scalaview

import org.scalatest._
import Utils._

class MyTestSuite extends FunSuite {

  test("1+2=3") {
    assert(1 + 2 === 3)
  }

  test("thin(1) a finite stream") {
    val s1 = (1 to 10).toStream
    val s2 = thinStream(s1, 1)
    assert(s2.toList === s1.toList)
  }

  test("thin(2) a finite stream") {
    val s1 = (1 to 10).toStream
    val s2 = thinStream(s1, 2)
    assert(s2.toList === List(2, 4, 6, 8, 10))
  }

  test("thin(3) a finite stream") {
    val s1 = (1 to 10).toStream
    val s2 = thinStream(s1, 3)
    assert(s2.toList === List(3, 6, 9))
  }

  test("rescale an image") {
    import java.awt.image.BufferedImage
    val im1 = new BufferedImage(100, 200, BufferedImage.TYPE_BYTE_GRAY)
    val im2 = biResize(im1, 1000, 2000)
    assert(im2.getWidth === 1000)
    assert(im2.getHeight === 2000)
  }

}

/* eof */

