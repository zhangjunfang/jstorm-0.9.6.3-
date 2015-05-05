package backtype.storm.testing;

import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Fields;

import java.util.Map;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static backtype.storm.utils.Utils.tuple;

public class TestWordCounter extends BaseBasicBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4387845417643741086L;

	public static Logger LOG = LoggerFactory.getLogger(TestWordCounter.class);

	Map<String, Integer> _counts;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
		_counts = new HashMap<String, Integer>();
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		String word = (String) input.getValues().get(0);
		int count = 0;
		if (_counts.containsKey(word)) {
			count = _counts.get(word);
		}
		count++;
		_counts.put(word, count);
		collector.emit(tuple(word, count));
	}

	@Override
	public void cleanup() {

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word", "count"));
	}

}