package com.athena.storm.spout

import java.util
import java.util.concurrent.LinkedBlockingQueue

import org.apache.storm.spout.SpoutOutputCollector
import org.apache.storm.task.TopologyContext
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichSpout
import org.apache.storm.tuple.{Fields, Values}
import twitter4j.conf.ConfigurationBuilder
import twitter4j.{StallWarning, Status, StatusDeletionNotice, StatusListener, TwitterObjectFactory, TwitterStream, TwitterStreamFactory}
import com.athena.storm.meta.TwitterConfiguration
class TweetSpout extends BaseRichSpout {

  var _collector: SpoutOutputCollector = _
  var _twitterStream: TwitterStream = _
  var consumerKey: String = TwitterConfiguration.CONSUMER_KEY
  var consumerSecret: String = TwitterConfiguration.CONSUMER_SECRET
  var accessToken: String = TwitterConfiguration.ACCESS_TOKEN
  var accessTokenSecret: String = TwitterConfiguration.ACCESS_TOKEN_SECRET
  var queue: LinkedBlockingQueue[Status] = _

  override def open(conf: util.Map[_, _], context: TopologyContext, collector: SpoutOutputCollector): Unit = {
    queue = new LinkedBlockingQueue[Status](1000)
    _collector = collector

    val listener: StatusListener = new StatusListener {
      override def onStatus(status: Status): Unit = {
        val rawJson: String =  TwitterObjectFactory.getRawJSON(status)
        println(s"Offering: $rawJson")
        queue.offer(status)
      }

      override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {

      }

      override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {

      }

      override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {

      }

      override def onStallWarning(warning: StallWarning): Unit = {

      }

      override def onException(ex: Exception): Unit = {
        println(ex)
      }
    }

    val configBuilder: ConfigurationBuilder = new ConfigurationBuilder()

    configBuilder.setJSONStoreEnabled(true)
    configBuilder
      .setDebugEnabled(true)
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    _twitterStream = new TwitterStreamFactory(configBuilder.build()).getInstance()
    _twitterStream.addListener(listener)
    _twitterStream.sample()
  }

  override def nextTuple(): Unit = {
    val ret = Option(queue.poll())
    if (ret.nonEmpty)
      _collector.emit(new Values(ret.get))
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("tweet-status"))
  }

  override def close(): Unit = {
    super.close()
    _twitterStream.shutdown()
  }
}
