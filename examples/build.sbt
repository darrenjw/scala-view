name := "scala-view-examples"

version := "0.1"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

libraryDependencies  ++= Seq(
            "org.scalacheck" %% "scalacheck" % "1.11.4" % "test",
            "org.scalatest" %% "scalatest" % "2.1.7" % "test",
            "org.scalanlp" %% "breeze" % "0.12",
            "org.scalanlp" %% "breeze-natives" % "0.12",
            "darrenjw" %% "scala-view" % "0.3-SNAPSHOT"
)

resolvers ++= Seq(
            "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/",
            "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
	"Newcastle mvn repo" at "https://www.staff.ncl.ac.uk/d.j.wilkinson/mvn/"
)

scalaVersion := "2.11.7"

