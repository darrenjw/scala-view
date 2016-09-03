# scala-view

Small Scala library for animating on-screen a Stream of AWT BufferedImages in a Swing JFrame


## Building and using the package (with sbt)

This is an sbt project, so building from source should be as simple as `sbt package` (assuming that sbt in installed and in your path).

The binary package is currently published to my own personal maven repo. To use it, add `"darrenjw" %% "scala-view" % "0.2"` to the sbt library dependencies for the "official" release, or `"darrenjw" %% "scala-view" % "0.3-SNAPSHOT"` for the latest version I've pushed, in the project which requires this package, and also add `"Newcastle mvn repo" at "https://www.staff.ncl.ac.uk/d.j.wilkinson/mvn/"` to the resolvers.

## Using the package in your code

The idea is that you create a `Stream` of `BufferedImage`s for your application and then visualise it by calling `scalaview.SwingImageViewer(sbi)` where `sbi` has type `Stream[BufferedImage]`. Further details can be found in the scaladoc (which can be built with `sbt doc`). Note that it is usually better to use the constructor(s) in the companion object for the `SwingImageViewer` rather than the default class constructor.

Examples of use can be found in the separate repo [scala-view-examples](https://github.com/darrenjw/scala-view-examples).
