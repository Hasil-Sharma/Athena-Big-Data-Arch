package com.athena.driver

import com.athena.storm.bolt.{ESWriteBolt, KafkaWriteBolt, MLProcessingBolt, TweetProcessingBolt}
import com.athena.storm.spout.TweetSpout
import org.apache.storm.{Config, LocalCluster, StormSubmitter}
import org.apache.storm.topology.TopologyBuilder

object TwitterTopology {

  def main(args: Array[String]): Unit = {
    val builder = new TopologyBuilder()

    builder
      .setSpout("tweet-spout", new TweetSpout(), 2)

    builder
      .setBolt("tweet-processing-bolt", new TweetProcessingBolt(), 2)
      .shuffleGrouping("tweet-spout")

    builder
      .setBolt("ml-processing-bolt",new MLProcessingBolt(), 4)
      .shuffleGrouping("tweet-processing-bolt")
    builder
      .setBolt("forwardToES", ESWriteBolt.getESBolt(), 2)
      .shuffleGrouping("ml-processing-bolt")

    val conf = new Config()
    conf.setDebug(false)
    if (args != null && args.length > 0){
      conf.setNumWorkers(9)
      StormSubmitter.submitTopology(args(0), conf, builder.createTopology())
    } else {
      conf.setMaxTaskParallelism(3)
      val cluster = new LocalCluster
      cluster.submitTopology("twitter-topology", conf, builder.createTopology())
      Thread.sleep(1000000)
      cluster.shutdown()
    }
  }

}
