package storm.trident.operation.impl;

import storm.trident.partition.IndexHashGrouping;

public class IndexHashBatchToPartition implements SingleEmitAggregator.BatchToPartition {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6736664127111222648L;

	@Override
    public int partitionIndex(Object batchId, int numPartitions) {
        return IndexHashGrouping.objectToIndex(batchId, numPartitions);
    }
    
}
