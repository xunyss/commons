package io.xunyss.commons.lang;

import java.util.regex.Pattern;

/**
 *
 * @author XUNYSS
 */
public class SimplePatternMatcher {
	
	/**
	 * regular expression specific characters
	 */
	private static final String REGEX_SPECIFIC_CHARS = "()[]{}$^+|.";
	
	/** pattern */
	private String pattern;
	/** ignore case */
	private boolean ignoreCase;
	
	/** regular expression of pattern */
	private String patternRegex;
	/** pattern object of pattern */
	private Pattern simplePattern;
	
	
	/**
	 *
	 * @param pattern
	 */
	public SimplePatternMatcher(final String pattern) {
		this(pattern, false);
	}
	
	/**
	 *
	 * @param pattern
	 * @param ignoreCase
	 */
	public SimplePatternMatcher(final String pattern, final boolean ignoreCase) {
		this.pattern = pattern;
		this.ignoreCase = ignoreCase;
		this.patternRegex = convertToRegex(pattern, ignoreCase);
		this.simplePattern = Pattern.compile(patternRegex);
	}
	
	/**
	 *
	 * @param input
	 * @return
	 */
	public boolean matches(String input) {
		return simplePattern.matcher(input).matches();
	}
	
	/**
	 *
	 * @return
	 */
	public String getPattern() {
		return pattern;
	}
	
	/**
	 *
	 * @return
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}
	
	/**
	 *
	 * @return
	 */
	public String getPatternRegex() {
		return patternRegex;
	}
	
	/**
	 *
	 * @return
	 */
	public Pattern getSimplePattern() {
		return simplePattern;
	}
	
	
	/**
	 *
	 * @param pattern
	 * @param input
	 * @param ignoreCase
	 * @return
	 */
	public static boolean matches(String pattern, String input, boolean ignoreCase) {
		return input.matches(convertToRegex(pattern, ignoreCase));
	}
	
	/**
	 *
	 * @param pattern
	 * @param input
	 * @return
	 */
	public static boolean matches(String pattern, String input) {
		return matches(pattern, input, false);
	}
	
	
	/**
	 *
	 * @param pattern
	 * @param ignoreCase
	 * @return
	 */
	private static String convertToRegex(String pattern, boolean ignoreCase) {
		StringBuilder regex = new StringBuilder();
		int len = pattern.length();
		char ch;
		for (int i = 0; i < len; i++) {
			ch = pattern.charAt(i);
			//--------------------------------------------------------------------------------------
			if (REGEX_SPECIFIC_CHARS.indexOf(ch) > -1) {
				regex.append("\\").append(ch);
				continue;
			}
			if (ch == '*') {
				regex.append(".*");
				continue;
			}
			if (ch == '?') {		// TODO
				regex.append("?");
				continue;
			}
			regex.append(ch);
			//--------------------------------------------------------------------------------------
		}
		
		return (ignoreCase ? "(?i)" : "") + "^" + regex.toString() + "$";
	}
}
