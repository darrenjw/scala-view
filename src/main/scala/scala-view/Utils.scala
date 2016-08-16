/*
package.scala

Utility methods in the package object

Copyright (C) 2016 Darren J Wilkinson
https://github.com/darrenjw

 */

package scalaview

import java.awt.image.BufferedImage

object Utils {

  def thinStream[T](s: Stream[T], th: Int): Stream[T] = {
    val ss = s.drop(th)
    ss.head #:: thinStream(ss, th)
  }

  def biResize(img: BufferedImage, newW: Int, newH: Int): BufferedImage = {
    val tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_REPLICATE);
    val dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
    val g2d = dimg.createGraphics();
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    dimg
  }

}

/* eof */
