package io.xunyss.openssl;

import java.io.IOException;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class OpenSSLTest {
	
	@Test
	public void help() throws IOException {
//		OpenSSL openssl = new OpenSSL(System.out);
//		openssl.exec("version");
		
		StringWriter sw = new StringWriter();
		OpenSSL openssl = new OpenSSL(sw);
		openssl.exec("version");
		
		System.err.println(sw.toString());
	}
	
	@Test
	public void testSSL() throws IOException {
		OpenSSL openssl = new OpenSSL(System.out);
		openssl.exec("asn1parse", "-genstr", "UTF8:hello world");
	}
	
	@Test
	public void testSSL2() throws IOException {
		OpenSSL openssl = new OpenSSL(System.out);
		openssl.exec("asn1parse -genstr UTF8:\"hello world\"");
	}
	
	
	@Test
	public void test() throws Exception {
		String s = "asn1parse -genstr UTF8:\"hello world\"";
		String regex = "\"([^\"]*)\"|(\\S+)";
		Matcher m = Pattern.compile(regex).matcher(s);
		while (m.find()) {
			System.out.println("... " + m.group(2));
		}
	}
}
