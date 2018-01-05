package io.xunyss.openssl;

import java.io.IOException;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class OpenSSLTest {
	
	@Test
	public void exec() throws IOException {
		OpenSSL openssl = new OpenSSL(System.out);
		openssl.exec("version");
	}
	
	@Test
	public void version() {
		OpenSSL openssl = new OpenSSL();
		String version = openssl.version();
		System.out.println("version: " + version);
	}
}
