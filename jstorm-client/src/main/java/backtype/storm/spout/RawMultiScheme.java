package backtype.storm.spout;

import java.util.List;

import backtype.storm.tuple.Fields;
import static backtype.storm.utils.Utils.tuple;
import static java.util.Arrays.asList;

public class RawMultiScheme implements MultiScheme {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8684698009461977572L;

	@Override
	public Iterable<List<Object>> deserialize(byte[] ser) {
		return asList(tuple(ser));
	}

	@Override
	public Fields getOutputFields() {
		return new Fields("bytes");
	}
}
