package backtype.storm.testing;

import backtype.storm.utils.RegisteredGlobalState;

import java.util.HashSet;
import java.util.Set;
@SuppressWarnings({"unchecked","rawtypes"})
public class AckFailMapTracker implements AckFailDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9050178009857247715L;
	String _acked;
	String _failed;

	public AckFailMapTracker() {
		_acked = RegisteredGlobalState.registerState(new HashSet());
		_failed = RegisteredGlobalState.registerState(new HashSet());
	}

	public boolean isAcked(Object id) {
		return ((Set) RegisteredGlobalState.getState(_acked)).contains(id);
	}

	public boolean isFailed(Object id) {
		return ((Set) RegisteredGlobalState.getState(_failed)).contains(id);
	}

	@Override
	public void ack(Object id) {
		((Set) RegisteredGlobalState.getState(_acked)).add(id);
	}

	@Override
	public void fail(Object id) {
		((Set) RegisteredGlobalState.getState(_failed)).add(id);
	}

}
