package com.athena.storm.bolt.utils


import twitter4j.Status
import net.liftweb.json._
import java.util.UUID.randomUUID

import scala.util.Random

case class Tweet(name: String, content: String, date: Long, location: Location, country: String, state: String, prediction: Prediction, uuid: String = randomUUID().toString)
case class Location(lat: Double = 0.0, lon: Double  = 0.0)
case class Prediction(
                       toxic: Boolean = Random.shuffle(Array(true, false).toList).head,
                       severeToxic: Boolean = Random.shuffle(Array(true, false).toList).head,
                       obscene: Boolean = Random.shuffle(Array(true, false).toList).head,
                       threat: Boolean = Random.shuffle(Array(true, false).toList).head,
                       insult: Boolean = Random.shuffle(Array(true, false).toList).head,
                       identityHate: Boolean = Random.shuffle(Array(true, false).toList).head
                     )

class StatusToJson {
  implicit val formats: DefaultFormats.type = DefaultFormats
  def getJson(status: Status): Tweet = {
    val name: String = status.getUser.getName
    val content: String = status.getText
    val date: Long = System.currentTimeMillis / 1000
    val sampleLogLat: LogLat = SampleLogLat.get()

    val tweet : Tweet = Tweet(
      name = name,
      content = content,
      date = date,
      location = Location(sampleLogLat.latitude, sampleLogLat.longitude),
      country = "US",
      state = "TX",
      prediction = Prediction()
    )

    tweet

  }
}
