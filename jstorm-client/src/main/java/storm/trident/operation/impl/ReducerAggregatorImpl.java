package storm.trident.operation.impl;

import backtype.storm.tuple.Values;

import java.util.Map;

import storm.trident.operation.Aggregator;
import storm.trident.operation.ReducerAggregator;
import storm.trident.operation.TridentCollector;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;
@SuppressWarnings({"unchecked","rawtypes"})
public class ReducerAggregatorImpl implements Aggregator<Result> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1639825329806910193L;
	ReducerAggregator _agg;
    
    public ReducerAggregatorImpl(ReducerAggregator agg) {
        _agg = agg;
    }
    
    @Override
	public void prepare(Map conf, TridentOperationContext context) {
        
    }
    
    @Override
	public Result init(Object batchId, TridentCollector collector) {
        Result ret = new Result();
        ret.obj = _agg.init();
        return ret;
    }
    
    @Override
	public void aggregate(Result val, TridentTuple tuple, TridentCollector collector) {
        val.obj = _agg.reduce(val.obj, tuple);
    }
    
    @Override
	public void complete(Result val, TridentCollector collector) {
        collector.emit(new Values(val.obj));        
    }
    
    @Override
	public void cleanup() {
        
    }
}
