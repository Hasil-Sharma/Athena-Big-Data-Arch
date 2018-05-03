package com.athena.storm.bolt

import com.athena.storm.bolt.utils.Tweet
import com.athena.storm.meta.ESConfiguration
import net.liftweb.json.DefaultFormats
import org.apache.storm.elasticsearch.common.DefaultEsTupleMapper
import org.apache.storm.tuple.ITuple
import net.liftweb.json.Serialization.write

class AthenaEsTupleMapper extends DefaultEsTupleMapper {

  override def getId(tuple: ITuple): String = {
    val tweetObj: Tweet =  tuple.getValueByField("tweet-predicted").asInstanceOf[Tweet]
    tweetObj.uuid
  }

  override def getIndex(tuple: ITuple): String = {
    ESConfiguration.INDEX
  }

  override def getType(tuple: ITuple): String = {
    ESConfiguration.TYPE
  }

  override def getSource(tuple: ITuple): String = {
    val tweetObj: Tweet =  tuple.getValueByField("tweet-predicted").asInstanceOf[Tweet]
    implicit val formats: DefaultFormats.type = DefaultFormats
    val string = write(tweetObj).getBytes("UTF-8")

    val out = new String(string, "UTF-8").replaceAll("[^\\x00-\\x7F]", "")
//    println("HERE: " + out)
    out
  }


}
