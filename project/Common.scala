import sbt._
import Keys._

object Common {

  val commonSettings = Seq(
    organization := "com.athena",
    scalaVersion := "2.11.12",
    scalacOptions += "-target:jvm-1.7",
    scalacOptions += "-Yresolve-term-conflict:package",
    javacOptions ++= Seq("-source", "1.7", "-target", "1.7"),
    resolvers ++= Seq(
      "clojars" at "https://clojars.org/repo/"
    )
  )

  def AthenaSubProject(name: String) : Project = (
    Project(name, file(name))
    settings(commonSettings:_*)
  )
}
