package storm.trident.testing;

import backtype.storm.tuple.Values;
import storm.trident.operation.BaseFunction;
import storm.trident.operation.TridentCollector;
import storm.trident.tuple.TridentTuple;

public class Split extends BaseFunction {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6018110881947416974L;

	@Override
    public void execute(TridentTuple tuple, TridentCollector collector) {
        for(String word: tuple.getString(0).split(" ")) {
            if(word.length() > 0) {
                collector.emit(new Values(word));
            }
        }
    }
    
}
