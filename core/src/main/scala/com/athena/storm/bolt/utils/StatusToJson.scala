package com.athena.storm.bolt.utils

import java.util.UUID

import twitter4j.Status
import net.liftweb.json._
import net.liftweb.json.Serialization.write
import java.util.UUID.randomUUID

case class Tweet(name: String, content: String, date: Long, location: Location, country: String, state: String, prediction: Prediction, uuid: String = randomUUID().toString)
case class Location(lat: Double = 0.0, lon: Double  = 0.0)
case class Prediction(
                       toxic: Boolean = false,
                       severeToxic: Boolean = false,
                       obscene: Boolean = false,
                       threat: Boolean = false,
                       insult: Boolean = false,
                       identityHate: Boolean = false
                     )

class StatusToJson {
  implicit val formats: DefaultFormats.type = DefaultFormats
  def getJson(status: Status): String = {
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
    val jsonString = write(tweet)
    jsonString

  }
}
