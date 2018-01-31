package io.xunyss.commons.exec;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the StreamHandler class.
 *
 * @author XUNYSS
 */
public class StreamHandlerTest {
	
	private String startMessage = null;
	private String stopMessage = null;
	
	
	@Test
	public void streamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new StreamHandler() {
			@Override
			public void start() {
				Assert.assertNull(stopMessage);
				startMessage = "started";
			}
			@Override
			public void stop() {
				Assert.assertNotNull(startMessage);
				stopMessage = "stopped";
			}
		});
		processExecutor.execute("cmd /c dir");
		
		Assert.assertEquals("started", startMessage);
		Assert.assertEquals("stopped", stopMessage);
	}
}
