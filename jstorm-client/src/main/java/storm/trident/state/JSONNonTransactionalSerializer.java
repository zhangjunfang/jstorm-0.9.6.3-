package storm.trident.state;

import java.io.UnsupportedEncodingException;

import backtype.storm.utils.Utils;


@SuppressWarnings("rawtypes")
public class JSONNonTransactionalSerializer implements Serializer {

    /**
	 * 
	 */
	private static final long serialVersionUID = -64086702800459482L;

	@Override
    public byte[] serialize(Object obj) {
        try {
            return Utils.to_json(obj).getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object deserialize(byte[] b) {
        try {
            return Utils.from_json(new String(b, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
}
