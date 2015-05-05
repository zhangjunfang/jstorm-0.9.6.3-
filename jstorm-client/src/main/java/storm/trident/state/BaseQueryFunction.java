package storm.trident.state;

import storm.trident.operation.BaseOperation;


public abstract class BaseQueryFunction<S extends State, T> extends BaseOperation implements QueryFunction<S, T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6231758594529523450L;
    
}
