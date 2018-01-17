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
	private Map<String, String> environmentMap = new HashMap<>();
	
	
	/**
	 *
	 * @param inherit
	 */
	public Environment(boolean inherit) {
		if (inherit) {
			environmentMap.putAll(System.getenv());
		}
	}
	
	/**
	 *
	 */
	public Environment() {
		this(false);
	}
	
	public String put(String key, String value) {
		return environmentMap.put(key, value);
	}
	
	public String remove(String key) {
		return environmentMap.remove(key);
	}
	
	
	/**
	 *
	 * @param environment
	 * @return
	 */
	public static String[] toStrings(Environment environment) {
		if (environment == null || environment.environmentMap.isEmpty()) {
			return null;
		}
		
		final String[] result = new String[environment.environmentMap.size()];
		int i = 0;
		for (final Entry<String, String> entry : environment.environmentMap.entrySet()) {
			final String key  = entry.getKey() == null ? "" : entry.getKey().toString();
			final String value = entry.getValue() == null ? "" : entry.getValue().toString();
			result[i] = key + "=" + value;
			i++;
		}
		return result;
	}
}
