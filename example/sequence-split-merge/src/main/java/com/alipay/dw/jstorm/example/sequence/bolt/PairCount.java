package com.alipay.dw.jstorm.example.sequence.bolt;

import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.alipay.dw.jstorm.example.TpsCounter;
import com.alipay.dw.jstorm.example.sequence.bean.Pair;

@SuppressWarnings("rawtypes")
public class PairCount implements IBasicBolt {
	/**
     * 
     */
    private static final long serialVersionUID = 7346295981904929419L;

    public static final Logger LOG = LoggerFactory.getLogger(PairCount.class);

	private AtomicLong  sum = new AtomicLong(0);
	
	private TpsCounter          tpsCounter;

	@Override
	public void prepare(Map conf, TopologyContext context) {
	    tpsCounter = new TpsCounter(context.getThisComponentId() + 
                ":" + context.getThisTaskId());
		
		LOG.info("Successfully do parepare " + context.getThisComponentId());
	}

	@Override
	public void execute(Tuple tuple, BasicOutputCollector collector) {
	    tpsCounter.count();
	    
		Long tupleId = tuple.getLong(0);
		Pair pair = (Pair)tuple.getValue(1);
        
        sum.addAndGet(pair.getValue());
        
        collector.emit(new Values(tupleId, pair));

	}

	@Override
	public void cleanup() {
	    tpsCounter.cleanup();
		LOG.info("Total receive value :" + sum);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ID", "PAIR"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
	    //  Auto-generated method stub
	    return null;
	}
}