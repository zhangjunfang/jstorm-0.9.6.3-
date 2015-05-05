package storm.trident.operation.builtin;

import storm.trident.operation.BaseFilter;
import storm.trident.tuple.TridentTuple;


public class Equals extends BaseFilter {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8639289556555717710L;

	@Override
    public boolean isKeep(TridentTuple tuple) {
        for(int i=0; i<tuple.size()-1; i++) {
            Object o1 = tuple.getValue(i);
            Object o2 = tuple.getValue(i+1);
            if (o1 == null) {
            	if (o2 != null) {
            		return false;
            	}
            }else if (o1.equals(o2) == false){
            	return false;
            }

        }
        return true;
    }
    
}