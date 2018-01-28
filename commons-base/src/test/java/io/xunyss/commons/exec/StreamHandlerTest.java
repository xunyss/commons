package io.xunyss.commons.exec;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for the StreamHandler class.
 *
 * @author XUNYSS
 */
public class StreamHandlerTest {
	
	@Ignore
	@Test
	public void streamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new StreamHandler() {
			 @Override
			 public void start() {
				 System.out.println("start handle stream");
			 }
			 @Override
			 public void stop() {
				 System.out.println("stop handle stream");
			 }
		 });
		 processExecutor.execute("cmd /c dir");
	}
	
	@Ignore
	@Test
	public void pumpStreamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(System.out));
		processExecutor.execute("cmd /c dir");
	}
}
