package storm.trident.testing;

import backtype.storm.task.IMetricsContext;
import storm.trident.state.ITupleCollection;
import backtype.storm.tuple.Values;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import storm.trident.state.OpaqueValue;
import storm.trident.state.State;
import storm.trident.state.StateFactory;
import storm.trident.state.ValueUpdater;
import storm.trident.state.map.*;
import storm.trident.state.snapshot.Snapshottable;
import storm.trident.util.LRUMap;
@SuppressWarnings({"unchecked","rawtypes"})
public class LRUMemoryMapState<T> implements Snapshottable<T>, ITupleCollection, MapState<T> {

    LRUMemoryMapStateBacking<OpaqueValue> _backing;
    SnapshottableMap<T> _delegate;

    public LRUMemoryMapState(int cacheSize, String id) {
        _backing = new LRUMemoryMapStateBacking(cacheSize, id);
        _delegate = new SnapshottableMap(OpaqueMap.build(_backing), new Values("$MEMORY-MAP-STATE-GLOBAL$"));
    }

    @Override
	public T update(ValueUpdater updater) {
        return _delegate.update(updater);
    }

    @Override
	public void set(T o) {
        _delegate.set(o);
    }

    @Override
	public T get() {
        return _delegate.get();
    }

    @Override
	public void beginCommit(Long txid) {
        _delegate.beginCommit(txid);
    }

    @Override
	public void commit(Long txid) {
        _delegate.commit(txid);
    }

    @Override
	public Iterator<List<Object>> getTuples() {
        return _backing.getTuples();
    }

    @Override
	public List<T> multiUpdate(List<List<Object>> keys, List<ValueUpdater> updaters) {
        return _delegate.multiUpdate(keys, updaters);
    }

    @Override
	public void multiPut(List<List<Object>> keys, List<T> vals) {
        _delegate.multiPut(keys, vals);
    }

    @Override
	public List<T> multiGet(List<List<Object>> keys) {
        return _delegate.multiGet(keys);
    }

    public static class Factory implements StateFactory {

        /**
		 * 
		 */
		private static final long serialVersionUID = -5579206458603367570L;
		String _id;
        int _maxSize;

        public Factory(int maxSize) {
            _id = UUID.randomUUID().toString();
            _maxSize = maxSize;
        }

        @Override
        public State makeState(Map conf, IMetricsContext metrics, int partitionIndex, int numPartitions) {
            return new LRUMemoryMapState(_maxSize, _id + partitionIndex);
        }
    }

    static ConcurrentHashMap<String, Map<List<Object>, Object>> _dbs = new ConcurrentHashMap<String, Map<List<Object>, Object>>();
    static class LRUMemoryMapStateBacking<T> implements IBackingMap<T>, ITupleCollection {

        public static void clearAll() {
            _dbs.clear();
        }
        Map<List<Object>, T> db;
        Long currTx;

        public LRUMemoryMapStateBacking(int cacheSize, String id) {
        	_dbs.putIfAbsent(id, new LRUMap<List<Object>, Object>(cacheSize));
            this.db = (Map<List<Object>, T>) _dbs.get(id);
        }

        @Override
        public List<T> multiGet(List<List<Object>> keys) {
            List<T> ret = new ArrayList();
            for (List<Object> key : keys) {
                ret.add(db.get(key));
            }
            return ret;
        }

        @Override
        public void multiPut(List<List<Object>> keys, List<T> vals) {
            for (int i = 0; i < keys.size(); i++) {
                List<Object> key = keys.get(i);
                T val = vals.get(i);
                db.put(key, val);
            }
        }

        @Override
        public Iterator<List<Object>> getTuples() {
            return new Iterator<List<Object>>() {

                private Iterator<Map.Entry<List<Object>, T>> it = db.entrySet().iterator();

                @Override
				public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
				public List<Object> next() {
                    Map.Entry<List<Object>, T> e = it.next();
                    List<Object> ret = new ArrayList<Object>();
                    ret.addAll(e.getKey());
                    ret.add(((OpaqueValue)e.getValue()).getCurr());
                    return ret;
                }

                @Override
				public void remove() {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            };
        }
    }
}
