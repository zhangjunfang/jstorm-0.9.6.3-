package storm.trident.state;

import storm.trident.operation.BaseOperation;


public abstract class BaseStateUpdater<S extends State> extends BaseOperation implements StateUpdater<S> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5052405563023972667L;
    
}
