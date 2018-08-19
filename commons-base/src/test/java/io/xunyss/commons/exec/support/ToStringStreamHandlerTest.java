package io.xunyss.commons.exec.support;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.exec.ProcessExecutor;

/**
 * Unit tests for the ToStringStreamHandler class.
 * 
 * @author XUNYSS
 */
public class ToStringStreamHandlerTest {
	
	@Test
	public void pumpToStringOutputHandler() throws IOException {
		ToStringStreamHandler toStringStreamHandler = new ToStringStreamHandler();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(toStringStreamHandler);
		
		processExecutor.execute("cmd /c echo hello world");
		String out1 = toStringStreamHandler.getOutputString();
		Assert.assertEquals("hello world", out1.replaceAll("[\r\n]", ""));
		
		processExecutor.execute("cmd /c echo hello xunyss");
		String out2 = toStringStreamHandler.getOutputString();
		Assert.assertEquals("hello xunyss", out2.replaceAll("[\r\n]", ""));
	}
}
