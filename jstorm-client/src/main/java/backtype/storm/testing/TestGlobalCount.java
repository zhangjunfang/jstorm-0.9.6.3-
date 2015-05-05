package backtype.storm.testing;

import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.task.OutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Fields;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Values;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestGlobalCount extends BaseRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1085274635700919031L;

	public static Logger LOG = LoggerFactory.getLogger(TestWordCounter.class);

	private int _count;
	OutputCollector _collector;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;
		_count = 0;
	}

	@Override
	public void execute(Tuple input) {
		_count++;
		_collector.emit(input, new Values(_count));
		_collector.ack(input);
	}

	@Override
	public void cleanup() {

	}

	public Fields getOutputFields() {
		return new Fields("global-count");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("global-count"));
	}
}
