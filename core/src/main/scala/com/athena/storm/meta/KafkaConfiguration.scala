package com.athena.storm.meta

// https://kafka.apache.org/documentation/#producerconfigs

object KafkaConfiguration {
  val BOOTSTRAP_SERVERS = "ec2-54-213-70-87.us-west-2.compute.amazonaws.com:9092,ec2-34-212-178-130.us-west-2.compute.amazonaws.com:9092"
  val ACKS = "1"
  val STRING_SERIALIZER = "org.apache.kafka.common.serialization.StringSerializer"
  val TOPIC_NAME = "athena"
}
