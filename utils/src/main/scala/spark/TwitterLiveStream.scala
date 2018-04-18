package spark

import org.apache.spark.{SparkConf, SparkContext}
import spark.meta.GetTweets


object TwitterLiveStream {
  def main(args: Array[String]): Unit = {
    val outputFile = args(0)
    val conf = new SparkConf().setAppName("Twitter Live Stream")
    val sc = new SparkContext(conf)
    val numPartitions = Array(10000, 10000)

    val distData = sc.parallelize(numPartitions, numPartitions.length)

    val tweetsRDD = distData.mapPartitions(lines => {
      lines.flatMap(x => {
        val tweets = new GetTweets()
        tweets.consumeTweets(x)
      })
    })
    tweetsRDD.saveAsTextFile(outputFile)
    sc.stop()
  }
}
