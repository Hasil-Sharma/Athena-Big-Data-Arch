import sbt.{ExclusionRule, _}

object Dependencies {
  val coreDependencies = Seq(
    "org.apache.storm" % "storm-core" % "1.2.1" % "provided",
    "org.apache.kafka" % "kafka_2.11" % "1.1.0",
    "org.apache.storm" % "storm-kafka-client" % "1.2.1",
    "org.apache.storm" % "storm-elasticsearch" % "1.2.1" exclude("org.elasticsearch.client", "rest"),
    "org.elasticsearch.client" % "elasticsearch-rest-client" % "6.2.4",
    "org.twitter4j" % "twitter4j-core" % "4.0.6",
    "org.twitter4j" % "twitter4j-stream" % "4.0.6",
    "net.liftweb" % "lift-json_2.11" % "3.2.0"
  )

  val utilsDependencies = Seq(
    "org.apache.spark" %% "spark-core" % "2.1.2" % "provided",
    "org.apache.spark" %% "spark-sql" % "2.1.2" % "provided",
    "org.apache.hadoop" % "hadoop-aws" % "2.7.0" % "provided",
    "org.twitter4j" % "twitter4j-core" % "4.0.6" % "provided",
    "org.twitter4j" % "twitter4j-stream" % "4.0.6" % "provided"
  )
}