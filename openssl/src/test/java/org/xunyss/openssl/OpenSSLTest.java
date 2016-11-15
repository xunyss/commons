package org.xunyss.openssl;

import org.junit.Test;

public class OpenSSLTest {
	
	@Test
	public void testSSL() throws Exception {
		OpenSSL openssl = new OpenSSL(System.out);
		openssl.exec(new String[] {"asn1parse", "-genstr", "UTF8:hello-World"});
		
	//	Thread.sleep(10000);
	}
}
