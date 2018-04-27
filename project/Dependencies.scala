import sbt.{ExclusionRule, _}

object Dependencies {
  val coreDependencies = Seq(
    "org.apache.storm" % "storm-core" % "1.2.1" % "provided",
    "org.apache.kafka" % "kafka_2.11" % "1.1.0" % "provided",
    "org.apache.storm" % "storm-kafka-client" % "1.2.1" % "provided",
    "org.twitter4j" % "twitter4j-core" % "4.0.6" % "provided",
    "org.twitter4j" % "twitter4j-stream" % "4.0.6" % "provided",
    "net.liftweb" % "lift-json_2.11" % "3.2.0" % "provided"
  )

  val utilsDependencies = Seq(
    "org.apache.spark" %% "spark-core" % "2.1.2" % "provided",
    "org.apache.spark" %% "spark-sql" % "2.1.2" % "provided",
    "org.apache.hadoop" % "hadoop-aws" % "2.7.0" % "provided",
    "org.twitter4j" % "twitter4j-core" % "4.0.6" % "provided",
    "org.twitter4j" % "twitter4j-stream" % "4.0.6" % "provided"

  )
}