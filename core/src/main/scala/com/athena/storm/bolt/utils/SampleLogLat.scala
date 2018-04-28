package com.athena.storm.bolt.utils

import java.io.InputStream
import scala.util.Random

case class LogLat(longitude:Double, latitude:Double)

object SampleLogLat {

  private val sampleLogLat = {
    val stream : InputStream = getClass.getResourceAsStream("/sample_lat_log.csv")
    val lines = scala.io.Source.fromInputStream( stream ).getLines
    lines.map({
      line => {
        val cols = line.split(",")
        LogLat(cols(0).toDouble, cols(1).toDouble)
      }
    }) toList
  }

  def get(): LogLat = {
    sampleLogLat(Random.nextInt(sampleLogLat.size))
  }

  def main(args: Array[String]): Unit = {
    println(get())
  }
}
