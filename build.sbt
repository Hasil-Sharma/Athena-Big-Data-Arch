import Dependencies._
import Common._

name := "athena"

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case _                             => MergeStrategy.first
  }
)

lazy val athena = (
    project in file(".")
    dependsOn (core, utils)
    aggregate (core, utils)
    settings commonSettings
    settings(libraryDependencies ++= coreDependencies)
    settings(
      mainClass in assembly := Some("com.athena.driver.WordCountTopology")
    )
  )

lazy val core =  (
    AthenSubProject("core")
    settings (libraryDependencies ++= coreDependencies)
    settings (
      assemblyJarName in assembly := "core.jar"
    )
  )

lazy val utils = (
    AthenSubProject("utils")
    settings(libraryDependencies ++= utilsDependencies)
  )

