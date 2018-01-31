package io.xunyss.commons.exec;

import java.io.File;
import java.io.IOException;

import io.xunyss.commons.exec.stream.StringOutputHandler;
import io.xunyss.commons.lang.StringUtils;
import io.xunyss.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

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
		environmentCommand = SystemUtils.IS_OS_WINDOWS ? "cmd /c set" : "sh -c env";
	}
	
	@Test
	public void setWorkingDirectory() throws IOException {
		String userHome = SystemUtils.getSystemProperty("user.home");
		
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setWorkingDirectory(new File(userHome));
		processExecutor.setStreamHandler(stringOutputHandler);
		processExecutor.execute("cmd /c dir /w");
		
		String output = stringOutputHandler.getOutputString("MS949");
		Assert.assertTrue(output.contains(userHome));
	}
	
	@Test
	public void notSetEnvironment() throws IOException {
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		processExecutor.execute(environmentCommand);
		
		Assert.assertEquals(
				currentProcessDisplayedEnvironmentVariablesCount(),
				StringUtils.countOccurrence(stringOutputHandler.getOutputString(), "=")
		);
	}
	
	@Test
	public void setEnvironment() throws IOException {
		Environment environment = new Environment();
		environment.put("xunyss_env", "xunyss_variable");
		environment.put("xunyss_key", "xunyss_value");
		
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		processExecutor.setEnvironment(environment);
		processExecutor.execute(environmentCommand);
		
		String output = stringOutputHandler.getOutputString("MS949");
		Assert.assertTrue(output.contains("xunyss_env=xunyss_variable"));
		Assert.assertTrue(output.contains("xunyss_key=xunyss_value"));
	}
	
	@Test
	public void setEnvironmentInherit() throws Exception {
		Environment environment = new Environment(true);
		environment.put("xunyss_env", "xunyss_variable");
		environment.put("xunyss_key", "xunyss_value");
		
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		processExecutor.setEnvironment(environment);
		processExecutor.execute(environmentCommand);
		
		String output = stringOutputHandler.getOutputString("MS949");
		Assert.assertTrue(output.contains("xunyss_env=xunyss_variable"));
		Assert.assertTrue(output.contains("xunyss_key=xunyss_value"));
		
		Assert.assertEquals(
				currentProcessDisplayedEnvironmentVariablesCount() + 2,
				StringUtils.countOccurrence(stringOutputHandler.getOutputString(), "=")
		);
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
	
	
	private int currentProcessDisplayedEnvironmentVariablesCount() {
		String[] envVariables = Environment.currentProcessEnvironment().toStrings();
		int envVariablesCount = envVariables.length;
		for (String envVariable: envVariables) {
			if (envVariable.startsWith("=")) {
				envVariablesCount--;
			}
		}
		return envVariablesCount;
	}
}
