package storm.trident.operation;

import java.util.Map;

public abstract class BaseMultiReducer<T> implements MultiReducer<T> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4982193546612444152L;


	@SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map conf, TridentMultiReducerContext context) {
    }


    @Override
    public void cleanup() {
    }
    
}
