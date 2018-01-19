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
	 * system environment variables
	 */
	private Map<String, String> environmentMap = new HashMap<>();
	
	
	/**
	 * constructor
	 * 
	 * @param inherit
	 */
	public Environment(boolean inherit) {
		/*
		 * 현재 프로세스의 환경변수 상속 여부
		 */
		if (inherit) {
			environmentMap.putAll(System.getenv());
		}
	}
	
	/**
	 *
	 */
	public Environment() {
		/*
		 * 현재 프로세스의 환경변수 상속 여부
		 * 기본값 : false
		 */
		this(false);
	}
	
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public String put(String key, String value) {
		return environmentMap.put(key, value);
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public String remove(String key) {
		return environmentMap.remove(key);
	}
	
	/**
	 * 
	 * @return
	 */
	public String[] toEnvironmentStrings() {
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
}
