package backtype.storm.scheduler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//: improve this by maintaining slot -> executors as well for more efficient operations
@SuppressWarnings({"unchecked","rawtypes"})
public class SchedulerAssignmentImpl implements SchedulerAssignment {
	/**
	 * topology-id this assignment is for.
	 */
	String topologyId;
	/**
	 * assignment detail, a mapping from executor to <code>WorkerSlot</code>
	 */
	Map<ExecutorDetails, WorkerSlot> executorToSlot;

	public SchedulerAssignmentImpl(String topologyId,
			Map<ExecutorDetails, WorkerSlot> executorToSlots) {
		this.topologyId = topologyId;
		this.executorToSlot = new HashMap<ExecutorDetails, WorkerSlot>(0);
		if (executorToSlots != null) {
			this.executorToSlot.putAll(executorToSlots);
		}
	}

	@Override
	public Set<WorkerSlot> getSlots() {
		return new HashSet(executorToSlot.values());
	}

	/**
	 * Assign the slot to executors.
	 * 
	 * @param slot
	 * @param executors
	 */
	public void assign(WorkerSlot slot, Collection<ExecutorDetails> executors) {
		for (ExecutorDetails executor : executors) {
			this.executorToSlot.put(executor, slot);
		}
	}

	/**
	 * Release the slot occupied by this assignment.
	 * 
	 * @param slot
	 */
	public void unassignBySlot(WorkerSlot slot) {
		List<ExecutorDetails> executors = new ArrayList<ExecutorDetails>();
		for (ExecutorDetails executor : this.executorToSlot.keySet()) {
			WorkerSlot ws = this.executorToSlot.get(executor);
			if (ws.equals(slot)) {
				executors.add(executor);
			}
		}

		// remove
		for (ExecutorDetails executor : executors) {
			this.executorToSlot.remove(executor);
		}
	}

	/**
	 * Does this slot occupied by this assignment?
	 * 
	 * @param slot
	 * @return
	 */
	@Override
	public boolean isSlotOccupied(WorkerSlot slot) {
		return this.executorToSlot.containsValue(slot);
	}

	@Override
	public boolean isExecutorAssigned(ExecutorDetails executor) {
		return this.executorToSlot.containsKey(executor);
	}

	@Override
	public String getTopologyId() {
		return this.topologyId;
	}

	@Override
	public Map<ExecutorDetails, WorkerSlot> getExecutorToSlot() {
		return this.executorToSlot;
	}

	/**
	 * Return the executors covered by this assignments
	 * 
	 * @return
	 */
	@Override
	public Set<ExecutorDetails> getExecutors() {
		return this.executorToSlot.keySet();
	}
}