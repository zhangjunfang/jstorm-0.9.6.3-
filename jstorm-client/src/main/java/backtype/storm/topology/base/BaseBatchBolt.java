package backtype.storm.topology.base;

import backtype.storm.coordination.IBatchBolt;

public abstract class BaseBatchBolt<T> extends BaseComponent implements
		IBatchBolt<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7309061260453541348L;

}
