package storm.trident.operation;

import java.io.Serializable;
import java.util.Map;

public interface Operation extends Serializable {
    @SuppressWarnings("rawtypes")
	void prepare(Map conf, TridentOperationContext context);
    void cleanup();
}
