package io.xunyss.commons.exec;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import io.xunyss.commons.exec.stream.ConsoleWriteStreamHandler;
import io.xunyss.commons.exec.stream.StringWriteStreamHandler;
import io.xunyss.commons.lang.SystemUtils;

/**
 * Unit tests for the ProcessExecutor class.
 *
 * @author XUNYSS
 */
public class ProcessExecutorTest {
	
	private String environmentCommand;
	
	
	@Before
	public void setup() {
		// 환경변수 조회 명령어
		environmentCommand = SystemUtils.IS_OS_WINDOWS ? "set" : "env";
	}
	
	@Test
	public void setWorkingDirectory() throws Exception {
		String userHome = SystemUtils.getSystemProperty("user.home");
		
		StringWriteStreamHandler stringResult = new StringWriteStreamHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setWorkingDirectory(new File(userHome));
		processExecutor.setStreamHandler(stringResult);
		processExecutor.execute("cmd /c dir /w");
		
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
		processExecutor.execute("cmd /c " + environmentCommand);
	}
	
	@Test
	public void setEnvironmentInherit() throws Exception {
		Environment environment = new Environment(true);
		environment.put("xunyss_env", "xunyss_variable");
		environment.put("xunyss_key", "xunyss_value");
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new ConsoleWriteStreamHandler());
		processExecutor.setEnvironment(environment);
		processExecutor.execute("cmd /c " + environmentCommand);
	}
	
	@Ignore
	@Test
	public void executeWinAppAsync() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		int exitValue = processExecutor.execute("notepad.exe");
		
		Assert.assertEquals(ProcessExecutor.EXITVALUE_NOT_EXITED, exitValue);
	}
	
	@Ignore
	@Test
	public void executeWinAppSync() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor(true);
		int exitValue = processExecutor.execute("notepad.exe");
		
		Assert.assertEquals(ProcessExecutor.EXITVALUE_NORMAL, exitValue);
	}
	
	@Test
	public void execStreamHandle() throws Exception {
		StreamHandler streamHandler = new ConsoleWriteStreamHandler();
//		streamHandler.setAutoCloseStreams(false);
		
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
