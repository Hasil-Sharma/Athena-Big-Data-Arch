package com.athena.storm.bolt

import java.util.Properties

import com.athena.storm.meta.KafkaConfiguration
import org.apache.storm.kafka.bolt.KafkaBolt
import org.apache.storm.kafka.bolt.mapper.{FieldNameBasedTupleToKafkaMapper, TupleToKafkaMapper}
import org.apache.storm.kafka.bolt.selector.DefaultTopicSelector


object KafkaWriteBolt {
  def getKafkaBolt(): KafkaBolt[Nothing, Nothing]  = {
    val props = new Properties()
    props.put("bootstrap.servers", KafkaConfiguration.BOOTSTRAP_SERVERS)
    props.put("acks", KafkaConfiguration.ACKS)
    props.put("key.serializer", KafkaConfiguration.STRING_SERIALIZER)
    props.put("value.serializer", KafkaConfiguration.STRING_SERIALIZER)

    val kafkaBolt = new KafkaBolt()
      .withProducerProperties(props)
      .withTopicSelector(new DefaultTopicSelector(KafkaConfiguration.TOPIC_NAME))
      .withTupleToKafkaMapper(new FieldNameBasedTupleToKafkaMapper("_", "tweet-object"))

    kafkaBolt
  }

}
