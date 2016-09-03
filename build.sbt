name := "scala-view"
organization := "darrenjw"
version := "0.3-SNAPSHOT"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalacheck" %% "scalacheck" % "1.11.4" % "test",
            "org.scalatest" %% "scalatest" % "2.1.7" % "test",
            "org.scala-lang" % "scala-swing" % "2.11.0-M7"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalaVersion := "2.11.7"

publishTo := Some(Resolver.sftp("Personal mvn repo", "unix.ncl.ac.uk", "/home/ucs/100/ndjw1/public_html/mvn"))


