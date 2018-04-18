import sbt._

object Dependencies {
  val coreDependencies = Seq(
    "org.apache.storm" % "storm-core" % "1.2.1",
    "org.twitter4j" % "twitter4j-core" % "4.0.6",
    "org.twitter4j" % "twitter4j-stream" % "4.0.6"
  )

  val utilsDependencies = Seq()
}
