package backtype.storm.planner;

import backtype.storm.task.IBolt;
import java.io.Serializable;

public class TaskBundle implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1518610164717606039L;
	public IBolt task;
	public int componentId;

	public TaskBundle(IBolt task, int componentId) {
		this.task = task;
		this.componentId = componentId;
	}

}