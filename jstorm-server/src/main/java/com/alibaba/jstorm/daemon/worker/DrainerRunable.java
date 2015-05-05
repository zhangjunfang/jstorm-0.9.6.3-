package com.alibaba.jstorm.daemon.worker;

import java.util.List;

import backtype.storm.messaging.IConnection;
import backtype.storm.messaging.TaskMessage;

import com.alibaba.jstorm.metric.JStormTimer;
import com.alibaba.jstorm.metric.MetricDef;
import com.alibaba.jstorm.metric.Metrics;
import com.alibaba.jstorm.utils.DisruptorRunable;
import com.alibaba.jstorm.utils.Pair;

//import com.alibaba.jstorm.message.zeroMq.ISendConnection;

/**
 * 
 * Tuple sender
 * 
 * @author yannian
 * 
 */
public class DrainerRunable extends DisruptorRunable{

	private static JStormTimer timer = Metrics.registerTimer(null, 
			MetricDef.DRAINER_TIME, null, Metrics.MetricType.WORKER);

	public DrainerRunable(WorkerData workerData) {
		super(workerData.getSendingQueue(), timer, 
				DrainerRunable.class.getSimpleName(), workerData.getActive());
		
		Metrics.registerQueue(null, MetricDef.DRAINER_QUEUE, queue, null, Metrics.MetricType.WORKER);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleEvent(Object event, boolean endOfBatch)
			throws Exception {

		Pair<IConnection, List<TaskMessage>> pair = (Pair<IConnection, List<TaskMessage>>)event;
		
		pair.getFirst().send(pair.getSecond());
		
	}

}
