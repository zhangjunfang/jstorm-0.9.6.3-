package backtype.storm.topology.base;

import backtype.storm.topology.IRichBolt;

public abstract class BaseRichBolt extends BaseComponent implements IRichBolt {
	/**
	 * 
	 */
	private static final long serialVersionUID = -380896109088367449L;

	@Override
	public void cleanup() {
	}
}
