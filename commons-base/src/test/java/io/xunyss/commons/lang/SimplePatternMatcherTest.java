package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class SimplePatternMatcherTest {
	
	@Test
	public void matches() {
		SimplePatternMatcher matcher = new SimplePatternMatcher("10.20.*.40");
		Assert.assertTrue(matcher.matches("10.20.30.40"));
		Assert.assertFalse(matcher.matches("10.20.30.50"));
	}
	
	@Test
	public void matchesWithIgnoreCase() {
		SimplePatternMatcher matcher = new SimplePatternMatcher("test*", true);
		Assert.assertTrue(matcher.matches("TEST TEST"));
		
		SimplePatternMatcher defaultMatcher = new SimplePatternMatcher("test*");
		Assert.assertFalse(defaultMatcher.matches("TEST TEST"));
	}
	
	@Test
	public void staticMatches() {
		Assert.assertTrue(SimplePatternMatcher.matches("h*o", "hello"));
		Assert.assertFalse(SimplePatternMatcher.matches("h*o", "HELLO"));
		Assert.assertTrue(SimplePatternMatcher.matches("h*o", true, "HELLO"));
	}
}
