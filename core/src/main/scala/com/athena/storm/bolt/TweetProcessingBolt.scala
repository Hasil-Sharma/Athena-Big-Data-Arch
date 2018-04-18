package com.athena.storm.bolt

import java.util

import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.base.BaseRichBolt
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.tuple.{Fields, Tuple, Values}
import twitter4j.Status

class TweetProcessingBolt extends BaseRichBolt{
  var _collector: OutputCollector = _

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    _collector = collector
  }

  override def execute(input: Tuple): Unit = {
    val tweetStatus: Status = input.getValueByField("tweet-status").asInstanceOf[Status]
    val tweet = tweetStatus.getText()
    _collector.emit(new Values(tweet))
  }


  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("tweet"))
  }

}
