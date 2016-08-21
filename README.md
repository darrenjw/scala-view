# scala-view

Tiny Scala package for animating on-screen a Stream of Swing BufferedImages in a JFrame


## Building and using the package (with sbt)

The binary package is not yet published to sonatype or similar. So, to use, download or clone this repo and do a `sbt publish-local`. This will build the package and store it in your ivy cache. Then add `"darrenjw" %% "scala-view" % "0.1"` to the sbt library dependencies in the project which requires this package.

## Using the package in your code

The idea is that you create a Stream of BufferedImages for your application and then visualise it by calling `scalaview.SwingImageViewer(sbi)` where `sbi` has type `Stream[BufferedImage]`. Further details can be found in the scaladoc (which can be built with `sbt doc`). Note that it is usually better to use the constructor(s) in the companion object for the `SwingImageViewer` rather than the default class constructor.

Examples of use can be found in the separate repo [scala-view-examples](https://github.com/darrenjw/scala-view-examples).
