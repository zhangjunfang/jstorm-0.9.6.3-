package backtype.storm.metric.api;
@SuppressWarnings({"unchecked","rawtypes"})
public class CombinedMetric implements IMetric {
	private final ICombiner _combiner;
	private Object _value;

	public CombinedMetric(ICombiner combiner) {
		_combiner = combiner;
		_value = _combiner.identity();
	}

	public void update(Object value) {
		_value = _combiner.combine(_value, value);
	}

	@Override
	public Object getValueAndReset() {
		Object ret = _value;
		_value = _combiner.identity();
		return ret;
	}
}
