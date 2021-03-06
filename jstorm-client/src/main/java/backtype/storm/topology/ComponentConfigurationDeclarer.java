package backtype.storm.topology;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface ComponentConfigurationDeclarer<T extends ComponentConfigurationDeclarer> {
	T addConfigurations(Map conf);

	T addConfiguration(String config, Object value);

	T setDebug(boolean debug);

	T setMaxTaskParallelism(Number val);

	T setMaxSpoutPending(Number val);

	@Deprecated
	T setNumTasks(Number val);
}
