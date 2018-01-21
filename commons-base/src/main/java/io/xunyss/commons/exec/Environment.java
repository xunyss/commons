package io.xunyss.commons.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author XUNYSS
 */
public class Environment {
	
	/**
	 *
	 */
	private static final Environment currentEnvironment = new Environment(true);
	
	
	/**
	 * system environment variables
	 */
	private Map<String, String> environmentMap = new HashMap<>();
	
	
	/**
	 * Constructor.
	 *
	 * @param inherit whether to inherit current process environment variables (default: false)
	 */
	public Environment(boolean inherit) {
		if (inherit) {
			environmentMap.putAll(System.getenv());
		}
	}
	
	/**
	 * Constructor.
	 */
	public Environment() {
		this(false);
	}
	
	
	/**
	 * Put environment variable.
	 *
	 * @param key key
	 * @param value value
	 * @return the previous value
	 */
	public String put(String key, String value) {
		return environmentMap.put(key, value);
	}
	
	/**
	 * Remove environment variable.
	 *
	 * @param key key
	 * @return the previous value
	 */
	public String remove(String key) {
		return environmentMap.remove(key);
	}
	
	
	/**
	 * Get the variable list as an array.
	 *
	 * @return
	 */
	public String[] toStrings() {
		if (/* environmentMap == null || */ environmentMap.isEmpty()) {
			return null;
		}
		
		final String[] result = new String[environmentMap.size()];
		int i = 0;
		for (final Entry<String, String> entry : environmentMap.entrySet()) {
			final String key  = entry.getKey() == null ? "" : entry.getKey().toString();
			final String value = entry.getValue() == null ? "" : entry.getValue().toString();
			result[i] = key + "=" + value;
			i++;
		}
		return result;
	}
	
	
	/**
	 * Returns current process environment.
	 *
	 * @return current process environment
	 */
	public static Environment currentProcessEnvironment() {
		return currentEnvironment;
	}
}
