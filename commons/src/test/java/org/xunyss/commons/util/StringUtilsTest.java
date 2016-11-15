package org.xunyss.commons.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author XUNYSS
 */
public class StringUtilsTest {

	@Test
	public void testStringUtils() {
		Assert.assertEquals(StringUtils.remove("hello", "he"), "llo");
	}
}
