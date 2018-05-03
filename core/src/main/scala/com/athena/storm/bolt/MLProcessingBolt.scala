package com.athena.storm.bolt

import java.net.SocketTimeoutException
import java.util

import com.athena.storm.bolt.utils.{Prediction, Tweet}
import com.athena.storm.meta.MLProcessingConfiguration
import net.liftweb.json._
import org.apache.storm.task.{OutputCollector, TopologyContext}
import org.apache.storm.topology.OutputFieldsDeclarer
import org.apache.storm.topology.base.BaseRichBolt
import org.apache.storm.tuple.{Fields, Tuple, Values}
import scalaj.http._

case class PredictionResponse(prediction: Array[Boolean])

class MLProcessingBolt extends BaseRichBolt {
  var _collector: OutputCollector = _

  override def declareOutputFields(declarer: OutputFieldsDeclarer): Unit = {
    declarer.declare(new Fields("tweet-predicted"))
  }

  override def prepare(stormConf: util.Map[_, _], context: TopologyContext, collector: OutputCollector): Unit = {
    _collector = collector

  }

  override def execute(input: Tuple): Unit = {
    implicit val formats = DefaultFormats
    val tweetObject: Tweet = input.getValueByField("tweet-object").asInstanceOf[Tweet]
    try {

      val response = Http(MLProcessingConfiguration.mlEndPoint)
        .postData(tweetObject.content.replaceAll("[^\\x00-\\x7F]", "")).asString
      if (response.code == 200) {
        val json = parse(response.body)
        val jsonObject = json.extract[PredictionResponse]
        val prediction: Prediction = Prediction(
          jsonObject.prediction(0),
          jsonObject.prediction(1),
          jsonObject.prediction(2),
          jsonObject.prediction(3),
          jsonObject.prediction(4),
          jsonObject.prediction(5)
        )

        tweetObject.prediction = Some(prediction)
      }
    } catch {
      case e: SocketTimeoutException => {
        _collector.reportError(e)
      }
    }
    _collector.emit(new Values(tweetObject))
  }
}
