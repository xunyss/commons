package io.xunyss.commons.lang;

/**
 * String utilities.
 *
 * @author XUNYSS
 */
public final class StringUtils {
	
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	
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
	 * Count the occurrences of the substring {@code sub} in string {@code str}.
	 *
	 * @param str string to search in
	 * @param sub string to search for
	 * @return
	 */
	public static int countOccurrence(String str, String sub) {
		if (isEmpty(str) || isEmpty(sub)) {
			return 0;
		}
		
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
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
