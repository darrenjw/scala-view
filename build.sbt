name := "scala-view"
organization := "com.github.darrenjw"
version := "0.5-SNAPSHOT"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.3" % "test",
  "org.scalafx"   %% "scalafx"   % "8.0.102-R11",
  "org.scala-lang.modules" %% "scala-swing" % "2.0.2"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
)

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.11","2.12.4")

//publishTo := Some(Resolver.sftp("Personal mvn repo", "unix.ncl.ac.uk", "/home/ucs/100/ndjw1/public_html/mvn"))



