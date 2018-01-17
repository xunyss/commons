package io.xunyss.commons.exec;

import io.xunyss.commons.exec.stream.ConsoleWriteStreamHandler;
import io.xunyss.commons.exec.stream.StringWriteStreamHandler;
import io.xunyss.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import sun.nio.cs.ext.EUC_KR;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 *
 * @author XUNYSS
 */
public class ProcessExecutorTest {
	
	private String environmentCommand;
	
	@Before
	public void setup() {
		environmentCommand = SystemUtils.IS_OS_WINDOWS ? "set" : "env";
	}
	
	
	@Test
	public void setWorkingDirectory() throws Exception {
		String userHome = System.getProperty("user.home");
		
		StringWriteStreamHandler stringResult = new StringWriteStreamHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringResult);
		processExecutor.setWorkingDirectory(userHome);
		processExecutor.executeConsole("dir /w");
		
		Assert.assertTrue(stringResult.getOutputString("MS949").contains(userHome));
	}
	
	@Test
	public void setEnvironment() throws Exception {
		Environment environment = new Environment();
		environment.put("xunyss_env", "xunyss_variable");
		environment.put("xunyss_key", "xunyss_value");
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new ConsoleWriteStreamHandler());
		processExecutor.setEnvironment(environment);
		processExecutor.executeConsole(environmentCommand);
	}
	
	@Test
	public void setEnvironmentInherit() throws Exception {
		Environment environment = new Environment(true);
		environment.put("xunyss_env", "xunyss_variable");
		environment.put("xunyss_key", "xunyss_value");
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new ConsoleWriteStreamHandler());
		processExecutor.setEnvironment(environment);
		processExecutor.executeConsole(environmentCommand);
	}
	
	@Test
	public void executeWinApp() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.execute("notepad.exe");
	}
	
	@Test
	public void execStreamHandle() throws Exception {
		StreamHandler streamHandler = new ConsoleWriteStreamHandler();
		streamHandler.setAutoCloseStreams(false);
		
//		StreamHandler streamHandler = new FileStreamHandler(new File("C:/downloads/test.log"));
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(streamHandler);
//		processExecutor.execute("cmd /c ipconfig /all");
		processExecutor.execute(
		"D:\\xdev\\git\\commons\\commons-openssl\\target\\classes\\io\\xunyss\\openssl\\binary\\win32\\openssl.exe asn1parse -genstr UTF8:\"hello world\"");
	}
	
	@Test
	public void execCommandLine() throws Exception {
		
	}
}
