package backtype.storm.topology.base;

import backtype.storm.topology.IComponent;
import java.util.Map;

public abstract class BaseComponent implements IComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3451500233699261898L;

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}
