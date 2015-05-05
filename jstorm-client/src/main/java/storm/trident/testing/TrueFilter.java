package storm.trident.testing;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class TrueFilter extends BaseFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1872039254377446845L;

	@Override
    public boolean isKeep(TridentTuple tuple) {
        return true;
    }
    
}
