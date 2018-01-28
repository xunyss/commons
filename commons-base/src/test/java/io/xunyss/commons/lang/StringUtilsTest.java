package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the StringUtils class.
 *
 * @author XUNYSS
 */
public class StringUtilsTest {
	
	@Test
	public void isEmpty() {
		Assert.assertTrue(StringUtils.isEmpty(""));
		Assert.assertTrue(StringUtils.isEmpty(null));
	}
	
	@Test
	public void isNotEmpty() {
		Assert.assertFalse(StringUtils.isNotEmpty(""));
		Assert.assertFalse(StringUtils.isNotEmpty(null));
	}
	
	@Test
	public void removeStart() {
		Assert.assertEquals(
				"llo",
				StringUtils.removeStart("hello", "he")
		);
	}
	
	@Test
	public void removeEnd() {
		Assert.assertEquals(
				"hel",
				StringUtils.removeEnd("hello", "lo")
		);
	}
}
