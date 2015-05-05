package storm.trident.operation;

import java.util.Map;

public class BaseOperation implements Operation {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3775975041756543111L;

	@SuppressWarnings("rawtypes")
	@Override
    public void prepare(Map conf, TridentOperationContext context) {
    }

    @Override
    public void cleanup() {
    }
    
}
