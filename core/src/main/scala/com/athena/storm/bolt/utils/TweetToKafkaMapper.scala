package com.athena.storm.bolt.utils

import org.apache.storm.kafka.bolt.mapper.TupleToKafkaMapper
import org.apache.storm.tuple.Tuple

class TweetToKafkaMapper extends TupleToKafkaMapper[String, String]{
  override def getKeyFromTuple(tuple: Tuple): String = ???

  override def getMessageFromTuple(tuple: Tuple): String = ???
}
