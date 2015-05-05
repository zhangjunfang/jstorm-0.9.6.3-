package backtype.storm.topology.base;

import backtype.storm.transactional.partitioned.IOpaquePartitionedTransactionalSpout;

public abstract class BaseOpaquePartitionedTransactionalSpout<T> extends
		BaseComponent implements IOpaquePartitionedTransactionalSpout<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6030700137533465520L;

}
