package backtype.storm.testing;

import backtype.storm.topology.OutputFieldsDeclarer;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.InprocMessaging;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
@SuppressWarnings({"unchecked","rawtypes"})
public class FeederSpout extends BaseRichSpout {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7444363155345525088L;
	private int _id;
	private Fields _outFields;
	private SpoutOutputCollector _collector;
	private AckFailDelegate _ackFailDelegate;

	public FeederSpout(Fields outFields) {
		_id = InprocMessaging.acquireNewPort();
		_outFields = outFields;
	}

	public void setAckFailDelegate(AckFailDelegate d) {
		_ackFailDelegate = d;
	}

	public void feed(List<Object> tuple) {
		feed(tuple, UUID.randomUUID().toString());
	}

	public void feed(List<Object> tuple, Object msgId) {
		InprocMessaging.sendMessage(_id, new Values(tuple, msgId));
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		_collector = collector;
	}

	@Override
	public void close() {

	}

	@Override
	public void nextTuple() {
		List<Object> toEmit = (List<Object>) InprocMessaging.pollMessage(_id);
		if (toEmit != null) {
			List<Object> tuple = (List<Object>) toEmit.get(0);
			Object msgId = toEmit.get(1);

			_collector.emit(tuple, msgId);
		} else {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public void ack(Object msgId) {
		if (_ackFailDelegate != null) {
			_ackFailDelegate.ack(msgId);
		}
	}

	@Override
	public void fail(Object msgId) {
		if (_ackFailDelegate != null) {
			_ackFailDelegate.fail(msgId);
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(_outFields);
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return new HashMap<String, Object>();
	}
}