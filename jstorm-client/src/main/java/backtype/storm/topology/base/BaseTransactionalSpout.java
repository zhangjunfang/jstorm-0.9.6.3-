package backtype.storm.topology.base;

import backtype.storm.transactional.ITransactionalSpout;

public abstract class BaseTransactionalSpout<T> extends BaseComponent implements
		ITransactionalSpout<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -957077109077926142L;

}
