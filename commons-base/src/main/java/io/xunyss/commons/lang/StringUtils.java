package io.xunyss.commons.lang;

/**
 * String utilities.
 *
 * @author XUNYSS
 */
public final class StringUtils {
	
	/**
	 * Constructor.
	 */
	private StringUtils() {
		// cannot create instance
	}
	
	/**
	 * Check if a string is empty ("") or {@code null}.
	 *
	 * @param str the string to check
	 * @return {@code true} if the string is empty or null
	 */
	public static boolean isEmpty(final String str) {
		return str == null || str.length() == 0;
	}
	
	/**
	 * Check if a string is not empty ("") and not {@code null}.
	 *
	 * @param str the string to check
	 * @return {@code true} if the string is not empty and not null
	 */
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}
	
	/**
	 * Remove a substring only if it is at the beginning of a source string.
	 *
	 * @param str source string
	 * @param remove remove string
	 * @return removed string
	 */
	public static String removeStart(final String str, final String remove) {
		if (str.startsWith(remove)) {
			return str.substring(remove.length());
		}
		return str;
	}
	
	/**
	 * Remove a substring only if it is at the end of a source string.
	 *
	 * @param str source string
	 * @param remove remove string
	 * @return removed string
	 */
	public static String removeEnd(final String str, final String remove) {
		if (str.endsWith(remove)) {
			return str.substring(0, str.length() - remove.length());
		}
		return str;
	}
}
