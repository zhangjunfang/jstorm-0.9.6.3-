package backtype.storm.topology;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.tuple.Tuple;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicBoltExecutor implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1040841720700290343L;

	public static Logger LOG = LoggerFactory.getLogger(BasicBoltExecutor.class);

	private IBasicBolt _bolt;
	private transient BasicOutputCollector _collector;

	public BasicBoltExecutor(IBasicBolt bolt) {
		_bolt = bolt;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		_bolt.declareOutputFields(declarer);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		_bolt.prepare(stormConf, context);
		_collector = new BasicOutputCollector(collector);
	}

	@Override
	public void execute(Tuple input) {
		_collector.setContext(input);
		try {
			_bolt.execute(input, _collector);
			_collector.getOutputter().ack(input);
		} catch (FailedException e) {
			if (e instanceof ReportedFailedException) {
				_collector.reportError(e);
			}
			_collector.getOutputter().fail(input);
		}
	}

	@Override
	public void cleanup() {
		_bolt.cleanup();
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return _bolt.getComponentConfiguration();
	}
}