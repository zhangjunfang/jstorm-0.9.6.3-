package backtype.storm.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.commons.io.input.ClassLoaderObjectInputStream;
@SuppressWarnings({"rawtypes"})
public class SerializableSerializer extends Serializer<Object> {

	@Override
	public void write(Kryo kryo, Output output, Object object) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		byte[] ser = bos.toByteArray();
		output.writeInt(ser.length);
		output.writeBytes(ser);
	}

	@SuppressWarnings("resource")
	@Override
	public Object read(Kryo kryo, Input input, Class c) {
		int len = input.readInt();
		byte[] ser = new byte[len];
		input.readBytes(ser);
		ByteArrayInputStream bis = new ByteArrayInputStream(ser);
		try {
			ClassLoaderObjectInputStream ois = new ClassLoaderObjectInputStream(
					kryo.getClassLoader(), bis);
			return ois.readObject();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
