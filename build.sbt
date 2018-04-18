import Dependencies._
import Common._


lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs@_*) => MergeStrategy.discard
    case _ => MergeStrategy.first
  }
)

lazy val athena = (
  project in file(".")
    dependsOn(core, utils)
    aggregate(core, utils)
    settings (commonSettings: _*)
    settings (libraryDependencies ++= coreDependencies)
    settings (
      mainClass in assembly := Some("com.athena.driver.TwitterTopology")
    )
    settings assemblySettings
  )

lazy val core = (
  AthenaSubProject("core")
    settings (libraryDependencies ++= coreDependencies)
    settings (
    assemblyJarName in assembly := "core.jar"
    )
    settings assemblySettings
  )

lazy val utils = (
  AthenaSubProject("utils")
    settings (libraryDependencies ++= utilsDependencies)
  )

