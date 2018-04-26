package com.athena.storm.bolt.utils

import twitter4j.Status
import net.liftweb.json._
import net.liftweb.json.Serialization.write

case class Tweet(name: String, content: String, location: Location, prediction: Prediction)
case class Location(country: String, state: String, latitude: Double = 0.0, longitude: Double  = 0.0)
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
    val tweet : Tweet = Tweet(name, content, Location("US", "TX"), Prediction())
    val jsonString = write(tweet)
    jsonString

  }
}
