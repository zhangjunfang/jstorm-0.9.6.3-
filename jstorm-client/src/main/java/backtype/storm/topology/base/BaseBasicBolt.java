package backtype.storm.topology.base;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IBasicBolt;

import java.util.Map;

public abstract class BaseBasicBolt extends BaseComponent implements IBasicBolt {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1818037075120434390L;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context) {
	}

	@Override
	public void cleanup() {
	}
}
