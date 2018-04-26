import Dependencies._
import Common._
import sbtassembly.AssemblyPlugin.autoImport.assemblyOption

//lazy val packageDependency = TaskKey[File]("assembly-package-dependency", "Produces the dependency artifact.")

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := name.value + ".jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs@_*) => MergeStrategy.discard
    case _ => MergeStrategy.first
  },
  assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = true, includeDependency = false)
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
    settings (
    assemblyJarName in assembly := "utils.jar"
    )
    settings assemblySettings
  )

