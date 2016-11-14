package org.xunyss.commons.util;

/**
 * 
 * @author XUNYSS
 */
public class StringUtils {
	
	private StringUtils() {
		/* cannot create instance */
	}

	public static String remove(final String srcStr, final String remStr) {
		if (srcStr.startsWith(remStr)) {
			return srcStr.substring(remStr.length());
		}
		return srcStr;
	}
	
	public static String removeLast(final String srcStr, final String remStr) {
		if (srcStr.endsWith(remStr)) {
			return srcStr.substring(0, srcStr.length() - remStr.length());
		}
		return srcStr;
	}
}
