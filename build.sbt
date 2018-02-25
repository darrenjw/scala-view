name := "scala-view"
organization := "com.github.darrenjw"
version := "0.4"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.0.1" % "test",
            "org.scala-lang.modules" %% "scala-swing" % "2.0.2"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalaVersion := "2.12.1"

crossScalaVersions := Seq("2.11.11","2.12.1")

//publishTo := Some(Resolver.sftp("Personal mvn repo", "unix.ncl.ac.uk", "/home/ucs/100/ndjw1/public_html/mvn"))



