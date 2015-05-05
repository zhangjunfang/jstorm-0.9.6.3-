package storm.trident.operation.impl;

import java.util.Map;

import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class TrueFilter implements Filter {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6301488255230909238L;

	@Override
    public boolean isKeep(TridentTuple tuple) {
        return true;
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map conf, TridentOperationContext context) {
    }

    @Override
    public void cleanup() {
    }
    
}
