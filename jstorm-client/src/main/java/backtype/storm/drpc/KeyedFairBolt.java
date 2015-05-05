package backtype.storm.drpc;

import backtype.storm.coordination.CoordinatedBolt.FinishedCallback;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicBoltExecutor;
import backtype.storm.topology.IBasicBolt;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;
import backtype.storm.utils.KeyedRoundRobinQueue;

import java.util.HashMap;
import java.util.Map;
@SuppressWarnings({"rawtypes"})
public class KeyedFairBolt implements IRichBolt, FinishedCallback {
	/**
	 * 
	 */
	private static final long serialVersionUID = -584591781880334229L;
	IRichBolt _delegate;
	KeyedRoundRobinQueue<Tuple> _rrQueue;
	Thread _executor;
	FinishedCallback _callback;

	public KeyedFairBolt(IRichBolt delegate) {
		_delegate = delegate;
	}

	public KeyedFairBolt(IBasicBolt delegate) {
		this(new BasicBoltExecutor(delegate));
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		if (_delegate instanceof FinishedCallback) {
			_callback = (FinishedCallback) _delegate;
		}
		_delegate.prepare(stormConf, context, collector);
		_rrQueue = new KeyedRoundRobinQueue<Tuple>();
		_executor = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						_delegate.execute(_rrQueue.take());
					}
				} catch (InterruptedException e) {

				}
			}
		});
		_executor.setDaemon(true);
		_executor.start();
	}

	@Override
	public void execute(Tuple input) {
		Object key = input.getValue(0);
		_rrQueue.add(key, input);
	}

	@Override
	public void cleanup() {
		_executor.interrupt();
		_delegate.cleanup();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		_delegate.declareOutputFields(declarer);
	}

	@Override
	public void finishedId(Object id) {
		if (_callback != null) {
			_callback.finishedId(id);
		}
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return new HashMap<String, Object>();
	}
}
