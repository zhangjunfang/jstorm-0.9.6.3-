package storm.trident.operation.builtin;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;

public class FilterNull extends BaseFilter {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5697091857872589956L;

	@Override
    public boolean isKeep(TridentTuple tuple) {
        for(Object o: tuple) {
            if(o==null) return false;
        }
        return true;
    }
}
