package io.xunyss.commons.io;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the ResourceUtils class.
 *
 * @author XUNYSS
 */
public class ResourceUtilsTest {
	
	@Test
	public void getJarFileURL() throws MalformedURLException {
		URL stringClassUrl = ResourceUtils.getResource("/java/lang/String.class");
		Assert.assertTrue(ResourceUtils.getJarFileURL(stringClassUrl).toString().endsWith("rt.jar"));
		
		URL jarResourceUrl = new URL("jar:file:/jar_file_path.jar!/resource/path");
		Assert.assertEquals("file:/jar_file_path.jar", ResourceUtils.getJarFileURL(jarResourceUrl).toString());
		
		// jar:file:/C:/xdev/git/commons/commons-openssl/target/commons-openssl-0.1.3-RELEASE.jar!/io/xunyss/openssl/binary/win32
		// file:/C:/xdev/git/commons/commons-openssl/target/commons-openssl-0.1.3-RELEASE.jar
	}
}
