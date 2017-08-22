package org.xunyss.commons.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author XUNYSS
 */
public class StringUtilsTest {

	@Test
	public void remove() {
		Assert.assertEquals(StringUtils.remove("hello", "he"), "llo");
	}

	@Test
	public void removeLast() {
		Assert.assertEquals(StringUtils.removeLast("helloh", "oh"), "hell");
	}
}
