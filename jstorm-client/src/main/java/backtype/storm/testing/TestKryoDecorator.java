package backtype.storm.testing;

import backtype.storm.serialization.IKryoDecorator;
import com.esotericsoftware.kryo.Kryo;

public class TestKryoDecorator implements IKryoDecorator {

	@Override
	public void decorate(Kryo k) {
		k.register(TestSerObject.class);
	}
}
