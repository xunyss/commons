package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the ToStringBuilder class.
 *
 * @author XUNYSS
 */
public class ToStringBuilderTest {
	
	@Test
	public void test() {
		ToStringBuilder toStringBuilder = new ToStringBuilder();
		Assert.assertNotNull(toStringBuilder);
	}
}
