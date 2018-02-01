package io.xunyss.openssl;

import java.io.IOException;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class OpenSSLExecutorTest {
	
	// Test "debug mode"
	@Test
	public void construct() {
		
	}
	
	@Test
	public void exec() throws IOException {
		OpenSSLEngine openSSLExecutor = OpenSSLEngine.getInstance();
		openSSLExecutor.execute("asn1parse -genstr UTF8:\"hello world\"");
	}
}
