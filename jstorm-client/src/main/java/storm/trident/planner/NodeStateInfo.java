package storm.trident.planner;

import java.io.Serializable;
import storm.trident.state.StateSpec;

public class NodeStateInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8695031075541579403L;
	public String id;
    public StateSpec spec;
    
    public NodeStateInfo(String id, StateSpec spec) {
        this.id = id;
        this.spec = spec;
    }
}
