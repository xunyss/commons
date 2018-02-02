package io.xunyss.openssl;

import java.io.IOException;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class OpenSSLTest {
	
	@Test
	public void execute() throws IOException {
		OpenSSL openssl = new OpenSSL();
		openssl.exec("rsa", "haha");
	}
	
	@Test
	public void version() {
		
	}
}
