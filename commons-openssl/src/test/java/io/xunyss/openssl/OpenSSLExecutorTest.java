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
		OpenSSLExecutor openSSLExecutor = OpenSSLExecutor.getInstance();
//		openSSLExecutor.exec(System.out, "asn1parse", "-genstr", "UTF8:hello world");
		
		String bin = "D:\\xdev\\git\\commons\\openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe";
	//	openSSLExecutor.execute(bin, "asn1parse", "-genstr", "UTF8:hello");
	}
}
