package com.alibaba.jstorm.daemon.supervisor;

import java.util.Map;

public interface SupervisorDaemon {

	public String getId();

	@SuppressWarnings("rawtypes")
	public Map getConf();

	public void ShutdownAllWorkers();

}
