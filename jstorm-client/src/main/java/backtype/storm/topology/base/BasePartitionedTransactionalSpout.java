package backtype.storm.topology.base;

import backtype.storm.transactional.partitioned.IPartitionedTransactionalSpout;

public abstract class BasePartitionedTransactionalSpout<T> extends
		BaseComponent implements IPartitionedTransactionalSpout<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -24856676507033932L;

}
