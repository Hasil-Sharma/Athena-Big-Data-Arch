package com.athena.driver

import com.athena.storm.bolt.{SplitSentenceBolt, WordCountBolt}
import com.athena.storm.spout.RandomSentenceSpout
import org.apache.storm.{Config, LocalCluster, StormSubmitter}
import org.apache.storm.topology.TopologyBuilder
import org.apache.storm.tuple.Fields

object WordCountTopology {
  def main(args: Array[String]): Unit = {
    println("Hello World")
    val builder = new TopologyBuilder

    builder
      .setSpout("spout", new RandomSentenceSpout, 5)

    builder
      .setBolt("split", new SplitSentenceBolt, 8)
      .shuffleGrouping("spout")

    builder
      .setBolt("count", new WordCountBolt, 12)
      .fieldsGrouping("split", new Fields("word"))

    val conf = new Config()
    conf.setDebug(true)

    if (args != null && args.length > 0) {
      conf.setNumWorkers(3)
      StormSubmitter.submitTopology(args(0), conf, builder.createTopology())
    } else {
      conf.setMaxTaskParallelism(3)
      val cluster = new LocalCluster
      cluster.submitTopology("word-count", conf, builder.createTopology())
      Thread.sleep(10000)
      cluster.shutdown()
    }

  }
}
