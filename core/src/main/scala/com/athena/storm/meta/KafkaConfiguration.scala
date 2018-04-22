package com.athena.storm.meta

object KafkaConfiguration {
  val BOOTSTRAP_SERVERS = "localhost:9092"
  val ACKS = "1"
  val STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer"
}
