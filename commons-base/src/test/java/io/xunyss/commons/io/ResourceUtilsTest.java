package io.xunyss.commons.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
	public void getResource() {
		String resName = "/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar";
		
		URL url = ResourceUtils.getResource(resName);
		Assert.assertEquals("javax.servlet-api-3.1.0.jar", FileUtils.getSimpleFilename(url.getPath()));
	}
	
	@Test
	public void getResourceAsFile() throws IOException {
		String resName = "/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar";
		
		URL url = ResourceUtils.getResource(resName);
		File file = ResourceUtils.getResourceAsFile(resName);
		
		int resSize = 0;
		try (InputStream resStream = url.openStream()) {
			resSize = IOUtils.copy(resStream, NullOutputStream.NULL_OUTPUT_STREAM);
		}
		
		Assert.assertEquals(resSize, file.length());
	}
	
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
