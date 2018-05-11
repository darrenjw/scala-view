/*
Utils.scala

Utility methods for scalaview

Copyright (C) 2016-2018 Darren J Wilkinson
https://github.com/darrenjw

 */

package scalaview

import java.awt.image.BufferedImage

object Utils {


  /**
    *  Take every th value from the stream s of type T
    * 
    *  @param s A Stream to be thinned
    *  @param th Thinning interval
    * 
    *  @return The thinned stream, with values of the same type as the input stream
    * 
    *  Note that in the context of the scala-view package, it will usally be computationally advantageous
    *  to thin a stream of some state of type T before that stream is mapped to a stream of images. This
    *  function is completely agnostic to the type of stream to be thinned.
    */
  def thinStream[T](s: Stream[T], th: Int): Stream[T] = {
    val ss = s.drop(th-1)
    if (ss.isEmpty) Stream.empty else
      ss.head #:: thinStream(ss.tail, th)
  }

  /**
    *  Resize a BufferedImage. Intended for blowing up small images to make them a sensible size for
    *  viewing on a modern high-res display
    * 
    *  @param img The image to be re-sized
    *  @param newW The desired width of the new image
    *  @param newH The desired height of the new image
    * 
    *  @return The resized BufferedImage
    */
  def biResize(img: BufferedImage, newW: Int, newH: Int): BufferedImage = {
    val tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_REPLICATE);
    val dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);
    val g2d = dimg.createGraphics();
    g2d.setColor(java.awt.Color.white)
    g2d.fillRect(0, 0, newW, newH)
    g2d.drawImage(tmp, 0, 0, null);
    g2d.dispose();
    dimg
  }

  import scalafx.scene.image.{ ImageView, Image }
  import scalafx.embed.swing.SwingFXUtils

  /**
    *  Resize a SFX Image. Intended for blowing up small images to make them a sensible size for
    *  viewing on a modern high-res display
    * 
    *  @param img The image to be re-sized
    *  @param newW The desired width of the new image
    *  @param newH The desired height of the new image
    * 
    *  @return The resized Image
    */
  def iResize(img: Image, newW: Int, newH: Int): Image = {
    val bi = SwingFXUtils.fromFXImage(img, null)
    val bir = biResize(bi, newW, newH)
    SwingFXUtils.toFXImage(bir, null)
  }


}

/* eof */
