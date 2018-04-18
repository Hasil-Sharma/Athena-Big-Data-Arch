package spark.meta

import java.util.concurrent.LinkedBlockingQueue

import spark.meta.{TwitterLiveStreamConfig => Configuration}

import scala.collection.mutable.ListBuffer

class GetTweets {

  val consumerKey: String = Configuration.CONSUMER_KEY
  val consumerSecret: String = Configuration.CONSUMER_SECRET
  val accessToken: String = Configuration.ACCESS_TOKEN
  val accessTokenSecret: String = Configuration.ACCESS_TOKEN_SECRET
  var _twitterStream: TwitterStream = _
  var queue: LinkedBlockingQueue[String] = _

  def consumeTweets(num: Int): List[String] = {

    queue = new LinkedBlockingQueue[String](num)
    val buffer : ListBuffer[String] = new ListBuffer[String]()
    val listener: StatusListener = new StatusListener {
      override def onStatus(status: Status): Unit = {
        queue.offer(TwitterObjectFactory.getRawJSON(status))
      }

      override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = ???

      override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = ???

      override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = ???

      override def onStallWarning(warning: StallWarning): Unit = ???

      override def onException(ex: Exception): Unit = {
        println(f"Exception $ex")
      }
    }

    val configBuilder: ConfigurationBuilder = new ConfigurationBuilder()
    configBuilder.setJSONStoreEnabled(true)
    configBuilder
      .setOAuthConsumerKey(consumerKey)
      .setOAuthConsumerSecret(consumerSecret)
      .setOAuthAccessToken(accessToken)
      .setOAuthAccessTokenSecret(accessTokenSecret)

    _twitterStream = new TwitterStreamFactory(configBuilder.build()).getInstance()
    _twitterStream.addListener(listener)
    _twitterStream.sample("en")

    while(buffer.size <= num){
      val ret = Option(queue.poll())
      if (ret.nonEmpty)
        buffer += ret.get
    }

    _twitterStream.shutdown()
    _twitterStream.cleanUp()

    buffer.toList
  }

}
