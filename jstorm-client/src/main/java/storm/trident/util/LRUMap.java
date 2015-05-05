package storm.trident.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUMap<A, B> extends LinkedHashMap<A, B> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4352994895869141763L;
	private int _maxSize;

    public LRUMap(int maxSize) {
        super(maxSize + 1, 1.0f, true);
        _maxSize = maxSize;
    }
    
    @Override
    protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
        return size() > _maxSize;
    }
}
