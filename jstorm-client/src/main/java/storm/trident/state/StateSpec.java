package storm.trident.state;

import java.io.Serializable;


public class StateSpec implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7957300022775038970L;
	public StateFactory stateFactory;
    public Integer requiredNumPartitions = null;
    
    public StateSpec(StateFactory stateFactory) {
        this.stateFactory = stateFactory;
    }
}
