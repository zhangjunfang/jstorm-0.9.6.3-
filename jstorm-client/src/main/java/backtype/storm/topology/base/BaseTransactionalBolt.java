package backtype.storm.topology.base;

import backtype.storm.transactional.TransactionAttempt;

public abstract class BaseTransactionalBolt extends
		BaseBatchBolt<TransactionAttempt> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7218335265354235240L;

}
