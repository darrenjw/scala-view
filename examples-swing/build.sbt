name := "scala-view-examples"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalatest" %% "scalatest" % "3.0.1" % "test",
            "org.scalanlp" %% "breeze" % "0.13",
            "org.scalanlp" %% "breeze-natives" % "0.13",
            "com.github.darrenjw" %% "scala-view" % "0.6-SNAPSHOT"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"
    )

scalaVersion := "2.12.4"

