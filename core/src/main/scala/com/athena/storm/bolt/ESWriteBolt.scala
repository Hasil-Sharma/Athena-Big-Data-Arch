package com.athena.storm.bolt

import com.athena.storm.meta.ESConfiguration
import org.apache.storm.elasticsearch.common.{EsConfig, EsTupleMapper}
import org.apache.http.message.BasicHeader

object ESWriteBolt {

  def getESBolt() : AthenaESBolt = {
    val esConfig: EsConfig = new EsConfig(ESConfiguration.ES_NODES)
    esConfig.withDefaultHeaders(Array(new BasicHeader("Content-Type", "application/json; charset=utf-8")))
    val tupleMapper: EsTupleMapper = new AthenaEsTupleMapper()
    new AthenaESBolt(esConfig, tupleMapper)
  }

}
