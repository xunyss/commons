package io.xunyss.commons.openssl;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.openssl.OpenSSL;

/**
 *
 * @author XUNYSS
 */
public class OpenSSLTest {
	
	@Test
	public void exec() throws IOException {
		OpenSSL openssl = new OpenSSL();
		openssl.exec("version");
		Assert.assertEquals("OpenSSL 1.0.2g  1 Mar 2016" + "\r\n", openssl.getOutput());
	}
}