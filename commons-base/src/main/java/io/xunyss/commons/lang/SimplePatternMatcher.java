package io.xunyss.commons.lang;

import java.util.regex.Pattern;

/**
 * Simple pattern matcher.
 *
 * @author XUNYSS
 */
public class SimplePatternMatcher {
	
	/**
	 * Regular expression specific characters.
	 */
	private static final String REGEX_SPECIFIC_CHARS = "()[]{}$^+|.";
	
	
//	/**
//	 * Simple pattern string.
//	 */
//	private String simplePattern;
//
//	/**
//	 * Whether to ignore case.
//	 */
//	private boolean ignoreCase;
	
	/**
	 * Pattern object of simple pattern string.
	 */
	private Pattern pattern;
	
	
	/**
	 * Constructor.
	 *
	 * @param simplePattern simple pattern string
	 * @param ignoreCase whether to ignore case
	 */
	public SimplePatternMatcher(final String simplePattern, final boolean ignoreCase) {
//		this.simplePattern = simplePattern;
//		this.ignoreCase = ignoreCase;
		pattern = compile(simplePattern, ignoreCase);
	}
	
	/**
	 * Constructor.
	 *
	 * @param simplePattern simple pattern string
	 */
	public SimplePatternMatcher(final String simplePattern) {
		this(simplePattern, false);
	}
	
	/**
	 * Check if input string matches simple pattern.
	 *
	 * @param input input string to test
	 * @return {@code true} if the supplied {@code input} matched, {@code false} if it didn't
	 */
	public boolean matches(String input) {
		return pattern.matcher(input).matches();
	}
	
	/**
	 * Convert to JDK Pattern object.
	 *
	 * @param simplePattern simple pattern string
	 * @param ignoreCase whether to ignore case
	 * @return the compiled Pattern object
	 */
	protected Pattern compile(final String simplePattern, final boolean ignoreCase) {
		/*
		 * 2018.01.27 XUNYSS
		 * private static 형태로 있던 메소드를 확장 가능한 인스턴스 메소드로 변경
		 */
		
		StringBuilder regex = new StringBuilder();
		int len = simplePattern.length();
		char ch;
		for (int i = 0; i < len; i++) {
			ch = simplePattern.charAt(i);
			//--------------------------------------------------------------------------------------
			// make regular expression
			//--------------------------------------------------------------------------------------
			if (REGEX_SPECIFIC_CHARS.indexOf(ch) > -1) {
				regex.append("\\").append(ch);
				continue;
			}
			if (ch == '*') {		// '*'
				regex.append(".*");
				continue;
			}
			if (ch == '?') {		// TODO: '?' - 한개 문자 처리
				regex.append("?");
				continue;
			}
			regex.append(ch);
		}
		
		// simple pattern string ==> java Pattern object
		return Pattern.compile(
				(ignoreCase ? "(?i)" : "") + "^" + regex.toString() + "$"
		);
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * Check if input string matches simple pattern.
	 *
	 * @param simplePattern simple pattern string
	 * @param ignoreCase whether to ignore case
	 * @param input input string to test
	 * @return {@code true} if the supplied {@code input} matched, {@code false} if it didn't
	 */
	public static boolean matches(String simplePattern, boolean ignoreCase, String input) {
		return new SimplePatternMatcher(simplePattern, ignoreCase).matches(input);
	}
	
	/**
	 * Check if input string matches simple pattern.
	 *
	 * @param pattern simple pattern string
	 * @param input input string to test
	 * @return {@code true} if the supplied {@code input} matched, {@code false} if it didn't
	 */
	public static boolean matches(String pattern, String input) {
		return matches(pattern, false, input);
	}
}
