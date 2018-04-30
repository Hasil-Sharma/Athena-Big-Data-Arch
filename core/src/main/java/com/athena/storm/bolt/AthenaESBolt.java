package com.athena.storm.bolt;

import org.apache.http.entity.StringEntity;
import org.apache.storm.elasticsearch.bolt.AbstractEsBolt;
import org.apache.storm.elasticsearch.common.EsConfig;
import org.apache.storm.elasticsearch.common.EsTupleMapper;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.elasticsearch.client.ResponseException;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;
import static org.apache.http.util.Args.notBlank;

public class AthenaESBolt extends AbstractEsBolt {
    private final EsTupleMapper tupleMapper;

    public AthenaESBolt(EsConfig esConfig, EsTupleMapper tupleMapper) {
        super(esConfig);
        this.tupleMapper = requireNonNull(tupleMapper);
    }

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        super.prepare(map, topologyContext, outputCollector);
    }

    static String getEndpoint(String index, String type, String id) {
        requireNonNull(index);
        notBlank(index, "index");

        StringBuilder sb = new StringBuilder();
        sb.append("/").append(index);
        if (!(type == null || type.isEmpty())) {
            sb.append("/").append(type);
        }
        if (!(id == null || id.isEmpty())) {
            sb.append("/").append(id);
        }
        return sb.toString();
    }

    @Override
    public void process(Tuple tuple) {
        try {
            String source = tupleMapper.getSource(tuple);
            String index = tupleMapper.getIndex(tuple);
            String type = tupleMapper.getType(tuple);
            String id = tupleMapper.getId(tuple);
            Map<String, String> params = tupleMapper.getParams(tuple, new HashMap<String, String>());

            client.performRequest("put", getEndpoint(index, type, id), params, new StringEntity(source));
            collector.ack(tuple);
        } catch (ResponseException e) {
//            collector.reportError(e);
            collector.ack(tuple);
        } catch (Exception e){
            collector.reportError(e);
            collector.fail(tuple);
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }
}
