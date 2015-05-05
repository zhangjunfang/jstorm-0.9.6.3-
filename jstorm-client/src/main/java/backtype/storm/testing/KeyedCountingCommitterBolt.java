package backtype.storm.testing;

import backtype.storm.transactional.ICommitter;

public class KeyedCountingCommitterBolt extends KeyedCountingBatchBolt
		implements ICommitter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2795608824544904888L;

}
