package io.xunyss.commons.exec;

import java.util.StringTokenizer;

import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class ProcessExecutorTest {
	
	@Test
	public void openssl() throws Exception {
		String binName = "C:\\xdev\\git\\commons\\openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe";
		
		ProcessExecutor processExecutor = new ProcessExecutor();
//		processExecutor.execute(binName, "asn1parse", "-genstr", "UTF8:\"hello world\"");
//		processExecutor.execute(binName, "asn1parse", "-genstr", "UTF8:\"hello", "", "world\"");

//		processExecutor.execute(binName, "version");
		
		processExecutor.execute1("cmd /c dir");
	}
	
	@Test
	public void dir() throws Exception {
		StreamHandler streamHandler = new StreamHandler();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(streamHandler);
		processExecutor.execute1("cmd", "/C", "dir");
	}
	
	@Test
	public void notepad() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		String cmd = "C:\\xdev\\git\\commons\\openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe asn1parse -genstr UTF8:\"hello world\"";
//		processExecutor.execute("notepad");
		processExecutor.execute1(cmd);
	}
	
	@Test
	public void token() {
		String cmd = "openssl.exe asn1parse -genstr UTF8:\"hello world\"";
		System.out.println(cmd.split(" ").length);
		StringTokenizer stk = new StringTokenizer(cmd);
		System.out.println(stk.countTokens());
	}
}
