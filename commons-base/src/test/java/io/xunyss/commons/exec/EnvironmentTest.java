package io.xunyss.commons.exec;

import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.lang.StringUtils;

/**
 * Unit tests for the Environment class.
 *
 * @author XUNYSS
 */
public class EnvironmentTest {
	
	@Test
	public void put() {
		Environment environment = new Environment();
		String oldValue = environment.put("ENV_LANG", "ko");
		String[] envStrings = environment.toStrings();
		
		Assert.assertTrue(StringUtils.isEmpty(oldValue));
		Assert.assertNotNull(envStrings);
		Assert.assertEquals(1, envStrings.length);
	}
	
	@Test
	public void remove() {
		Environment environment = new Environment();
		environment.put("ENV_LANG", "ko");
		environment.put("ENV_LANGUAGE", "ko");
		String oldValue = environment.remove("ENV_LANG");
		String[] envStrings = environment.toStrings();
		
		Assert.assertEquals("ko", oldValue);
		Assert.assertNotNull(envStrings);
		Assert.assertEquals(1, envStrings.length);
		Assert.assertEquals("{ENV_LANGUAGE=ko}", environment.toString());
	}
	
	@Test
	public void currentProcessEnvironment() {
		Environment environment = Environment.currentProcessEnvironment();
		String[] envStrings = environment.toStrings();
		
		Assert.assertNotNull(envStrings);
		Assert.assertEquals(System.getenv().size(), envStrings.length);
	}
}
