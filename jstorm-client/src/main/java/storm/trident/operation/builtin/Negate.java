package storm.trident.operation.builtin;

import java.util.Map;

import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class Negate implements Filter {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -4454700032372337520L;
	Filter _delegate;
    
    public Negate(Filter delegate) {
        _delegate = delegate;
    }

    @Override
    public boolean isKeep(TridentTuple tuple) {
        return !_delegate.isKeep(tuple);
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map conf, TridentOperationContext context) {
        _delegate.prepare(conf, context);
    }

    @Override
    public void cleanup() {
        _delegate.cleanup();
    }
    
}
