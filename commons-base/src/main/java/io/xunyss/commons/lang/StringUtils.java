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
