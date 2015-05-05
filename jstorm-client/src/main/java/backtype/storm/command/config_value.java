package backtype.storm.command;

import java.security.InvalidParameterException;
import java.util.Map;

import backtype.storm.utils.Utils;

/**
 * Get configuration 
 * 
 * @author longda
 * 
 */
public class config_value {

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		if (args == null || args.length == 0) {
			throw new InvalidParameterException("Should input key name");
		}

		String key = args[0];

		Map conf = Utils.readStormConfig();

		System.out.print("VALUE: " + String.valueOf(conf.get(key)));
	}

}
