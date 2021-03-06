// sonatype stuff...

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false }

licenses := Seq("APL2" -> url("https://opensource.org/licenses/Apache-2.0"))

homepage := Some(url("https://github.com/darrenjw/scala-view/blob/master/README.md"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/darrenjw/scala-view"),
    "scm:git@github.com:darrenjw/scala-view.git"
  )
)

pomExtra := (
  <developers>
      <developer>
        <id>darrenjw</id>
        <name>Darren J Wilkinson</name>
        <url>https://github.com/darrenjw/</url>
      </developer>
   </developers>
)

//developers := List(
//  Developer(
//    id    = "darrenjw",
//    name  = "Darren J Wilkinson",
//    email = "darrenjwilkinson@btinternet.com",
//    url   = url("https://github.com/darrenjw")
//  )
//)

publishMavenStyle := true

publishArtifact in Test := false



// eof


