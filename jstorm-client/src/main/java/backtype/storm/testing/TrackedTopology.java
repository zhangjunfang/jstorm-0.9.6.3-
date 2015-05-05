package backtype.storm.testing;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.generated.StormTopology;
import clojure.lang.Keyword;
@SuppressWarnings({"unchecked","rawtypes"})
public class TrackedTopology extends HashMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = -806746790911073005L;

	public TrackedTopology(Map map) {
		super(map);
	}

	public StormTopology getTopology() {
		return (StormTopology) get(Keyword.intern("topology"));
	}
}
