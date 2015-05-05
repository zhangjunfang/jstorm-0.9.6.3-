package storm.trident.operation.builtin;

import storm.trident.state.ITupleCollection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import storm.trident.operation.TridentCollector;
import storm.trident.state.BaseQueryFunction;
import storm.trident.state.State;
import storm.trident.tuple.TridentTuple;
@SuppressWarnings({"unchecked","rawtypes"})
public class TupleCollectionGet extends BaseQueryFunction<State, Iterator<List<Object>>> {

    /**
	 * 
	 */
	private static final long serialVersionUID = -3252130383357546661L;

	@Override
    public List<Iterator<List<Object>>> batchRetrieve(State state, List<TridentTuple> args) {
        List<Iterator<List<Object>>> ret = new ArrayList(args.size());
        for(int i=0; i<args.size(); i++) {
            ret.add(((ITupleCollection)state).getTuples());
        }
        return ret;
    }

    @Override
    public void execute(TridentTuple tuple, Iterator<List<Object>> tuplesIterator, TridentCollector collector) {
        while(tuplesIterator.hasNext()) {
            collector.emit(tuplesIterator.next());
        }
    }
}
