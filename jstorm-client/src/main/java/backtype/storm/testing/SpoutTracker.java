package backtype.storm.testing;

import backtype.storm.spout.ISpoutOutputCollector;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.utils.RegisteredGlobalState;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
@SuppressWarnings({"rawtypes"})
public class SpoutTracker extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2917432917751584298L;
	IRichSpout _delegate;
	SpoutTrackOutputCollector _tracker;
	String _trackId;

	private class SpoutTrackOutputCollector implements ISpoutOutputCollector {
		@SuppressWarnings("unused")
		public int transferred = 0;
		@SuppressWarnings("unused")
		public int emitted = 0;
		public SpoutOutputCollector _collector;

		public SpoutTrackOutputCollector(SpoutOutputCollector collector) {
			_collector = collector;
		}

		private void recordSpoutEmit() {
			Map stats = (Map) RegisteredGlobalState.getState(_trackId);
			((AtomicInteger) stats.get("spout-emitted")).incrementAndGet();

		}

		@Override
		public List<Integer> emit(String streamId, List<Object> tuple,
				Object messageId) {
			List<Integer> ret = _collector.emit(streamId, tuple, messageId);
			recordSpoutEmit();
			return ret;
		}

		@Override
		public void emitDirect(int taskId, String streamId, List<Object> tuple,
				Object messageId) {
			_collector.emitDirect(taskId, streamId, tuple, messageId);
			recordSpoutEmit();
		}

		@Override
		public void reportError(Throwable error) {
			_collector.reportError(error);
		}
	}

	public SpoutTracker(IRichSpout delegate, String trackId) {
		_delegate = delegate;
		_trackId = trackId;
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_tracker = new SpoutTrackOutputCollector(collector);
		_delegate.open(conf, context, new SpoutOutputCollector(_tracker));
	}

	@Override
	public void close() {
		_delegate.close();
	}

	@Override
	public void nextTuple() {
		_delegate.nextTuple();
	}

	@Override
	public void ack(Object msgId) {
		_delegate.ack(msgId);
		Map stats = (Map) RegisteredGlobalState.getState(_trackId);
		((AtomicInteger) stats.get("processed")).incrementAndGet();
	}

	@Override
	public void fail(Object msgId) {
		_delegate.fail(msgId);
		Map stats = (Map) RegisteredGlobalState.getState(_trackId);
		((AtomicInteger) stats.get("processed")).incrementAndGet();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		_delegate.declareOutputFields(declarer);
	}

}
