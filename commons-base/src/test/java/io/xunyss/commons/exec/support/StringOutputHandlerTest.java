package io.xunyss.commons.exec.support;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.exec.ProcessExecutor;

/**
 * Unit tests for the StringOutputHandler class.
 * 
 * @author XUNYSS
 */
public class StringOutputHandlerTest {
	
	@Test
	public void pumpToStringOutputHandler() throws IOException {
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		
		processExecutor.execute("cmd /c echo hello world");
		String out1 = stringOutputHandler.getOutputString();
		Assert.assertEquals("hello world", out1.replaceAll("[\r\n]", ""));
		
		processExecutor.execute("cmd /c echo hello xunyss");
		String out2 = stringOutputHandler.getOutputString();
		Assert.assertEquals("hello xunyss", out2.replaceAll("[\r\n]", ""));
	}
}
