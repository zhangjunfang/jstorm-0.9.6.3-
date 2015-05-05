package backtype.storm.testing;

import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import java.util.HashMap;
import java.util.Map;

public class BoltTracker extends NonRichBoltTracker implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7214561072511158833L;
	IRichBolt _richDelegate;

	public BoltTracker(IRichBolt delegate, String id) {
		super(delegate, id);
		_richDelegate = delegate;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		_richDelegate.declareOutputFields(declarer);
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return new HashMap<String, Object>();
	}
}
