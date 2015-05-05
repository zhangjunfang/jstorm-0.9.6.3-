package backtype.storm.spout;

import backtype.storm.tuple.Fields;
import java.util.List;
import static backtype.storm.utils.Utils.tuple;

public class RawScheme implements Scheme {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3432313862210530606L;

	@Override
	public List<Object> deserialize(byte[] ser) {
		return tuple(ser);
	}

	@Override
	public Fields getOutputFields() {
		return new Fields("bytes");
	}
}
