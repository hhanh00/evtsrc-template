import sbt._
import Keys._

object EvtTest extends Build {
  lazy val root = Project(id = "evttest",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      resolvers += "Eligosource Releases" at "http://repo.eligotech.com/nexus/content/repositories/eligosource-releases",
      libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-actor" % "2.2.0",
        "org.eligosource" %% "eventsourced-core" % "0.6.0",
        "org.eligosource" %% "eventsourced-journal-leveldb" % "0.6.0")))
}