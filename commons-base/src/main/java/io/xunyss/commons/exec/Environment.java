package io.xunyss.commons.exec;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 
 * @author XUNYSS
 */
public final class Environment {
	
	/**
	 * Current process environment variables.
	 */
	private static final Environment currentEnvironment = new Environment(true);
	
	
	/**
	 * System environment variables.
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
	 * Get environment variable.
	 *
	 * @param key key
	 * @return value
	 */
	public String get(String key) {
		return environmentMap.get(key);
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
	 * @return array of key=value assignment strings
	 */
	public String[] toStrings() {
		if (/* environmentMap == null || */ environmentMap.isEmpty()) {
//			return ArrayUtils.EMPTY_STRING_ARRAY;
			return null;
		}
		
		final String[] result = new String[environmentMap.size()];
		int i = 0;
		for (final Entry<String, String> entry : environmentMap.entrySet()) {
			final String key = entry.getKey() == null ? "" : entry.getKey()/*.toString()*/;
			final String value = entry.getValue() == null ? "" : entry.getValue()/*.toString()*/;
			
			result[i] = key + "=" + value;
			i++;
		}
		return result;
	}
	
	@Override
	public String toString() {
		return environmentMap.toString();
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * Returns current process environment.
	 *
	 * @return current process environment
	 */
	public static Environment currentProcessEnvironment() {
		return currentEnvironment;
	}
}
