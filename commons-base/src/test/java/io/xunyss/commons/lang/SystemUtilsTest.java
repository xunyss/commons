package io.xunyss.commons.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the SystemUtils class.
 *
 * @author XUNYSS
 */
public class SystemUtilsTest {
	
	@Test
	public void getSystemProperty() {
		String userLanguage = SystemUtils.getSystemProperty("user.language");
		Assert.assertEquals("ko", userLanguage);
	}
}
