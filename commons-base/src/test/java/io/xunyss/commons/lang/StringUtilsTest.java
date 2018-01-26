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
	public void removeStart() {
		Assert.assertEquals(
				StringUtils.removeStart("hello", "he"),
				"llo"
		);
	}
	
	@Test
	public void removeEnd() {
		Assert.assertEquals(
				StringUtils.removeEnd("hello", "lo"),
				"hel"
		);
	}
}
