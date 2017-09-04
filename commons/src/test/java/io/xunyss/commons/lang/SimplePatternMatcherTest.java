package io.xunyss.commons.lang;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class SimplePatternMatcherTest {
	
	@Test
	public void properties() {
		final String regex = "^io\\.xunyss\\..*$";
		SimplePatternMatcher matcher = new SimplePatternMatcher("io.xunyss.*");
		Assert.assertEquals(
				matcher.getPattern(),
				"io.xunyss.*"
		);
		Assert.assertEquals(
				matcher.isIgnoreCase(),
				false
		);
		Assert.assertEquals(
				matcher.getPatternRegex(),
				regex
		);
		Assert.assertEquals(
				matcher.getSimplePattern().pattern(),
				Pattern.compile(regex).pattern()
		);
	}
	
	@Test
	public void matches() {
		SimplePatternMatcher matcher = new SimplePatternMatcher("10.20.*.40");
		Assert.assertEquals(
				matcher.matches("10.20.30.40"),
				true
		);
		Assert.assertEquals(
				matcher.matches("10.20.30.50"),
				false
		);
	}
	
	@Test
	public void staticMatches() {
		Assert.assertEquals(
				SimplePatternMatcher.matches("h*o", "hello"),
				true
		);
		Assert.assertEquals(
				SimplePatternMatcher.matches("h*o", "HELLO"),
				false
		);
		Assert.assertEquals(
				SimplePatternMatcher.matches("h*o", "hello", true),
				true
		);
		Assert.assertEquals(
				SimplePatternMatcher.matches("h*o", "HELLO", true),
				true
		);
	}
}
