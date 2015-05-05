package backtype.storm.serialization;

import backtype.storm.tuple.Tuple;

public interface ITupleDeserializer {
	Tuple deserialize(byte[] ser);
}
