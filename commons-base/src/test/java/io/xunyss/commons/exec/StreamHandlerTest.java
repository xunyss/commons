package io.xunyss.commons.exec;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for the StreamHandler class.
 *
 * @author XUNYSS
 */
public class StreamHandlerTest {
	
	private String command = "cmd /c dir";
	
	
	@Ignore
	@Test
	public void streamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new StreamHandler() {
			@Override
			public void start() {
				System.out.println("Start handle process streams");
			}
			@Override
			public void stop() {
				System.out.println("Stop handle process streams");
			}
		});
		processExecutor.execute(command);
	}
}
