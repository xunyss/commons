package io.xunyss.commons.exec;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author XUNYSS
 */
public class ProcessExecutorTest {
	
	@Before
	public void setup() {
		
	}
	
	
	@Test
	public void setWorkingDirectory() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setWorkingDirectory(System.getProperty("user.home"));
		processExecutor.execute("cmd /c dir");
	}
	
	@Test
	public void setEnvironment() throws Exception {
		Environment environment = new Environment();
		environment.put("XUNY_ENV", "SONGJUNGHUN");
		environment.put("XUNY_KEY", "XUNY_VALUE");
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setEnvironment(environment);
		processExecutor.execute("cmd /c set");
	}
	
	@Test
	public void setEnvironmentInherit() throws Exception {
		Environment environment = new Environment(true);
		environment.put("XUNY_ENV", "SONGJUNGHUN");
		environment.put("XUNY_KEY", "XUNY_VALUE");
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new StreamHandler(System.out, System.out));
		processExecutor.setEnvironment(environment);
		processExecutor.executeCommandLine("set");
	}
	
	@Test
	public void execAcync() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		Process p = processExecutor.execute("notepad.exe");
	}
	
	//@Test
	public void execStreamHandle() throws Exception {
		StreamHandler streamHandler = new StreamHandler(System.out, System.out);
		streamHandler.setAutoCloseStreams(false);
		
//		StreamHandler streamHandler = new FileStreamHandler(new File("C:/downloads/test.log"));
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(streamHandler);
//		processExecutor.execute("cmd /c ipconfig /all");
		processExecutor.execute(
				"C:\\xdev\\git\\commons\\openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe asn1parse -genstr UTF8:\"hello world\"");
	}
	
	@Test
	public void execCommandLine() throws Exception {
		
	}
}
