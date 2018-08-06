package io.xunyss.commons.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	
	@Test
	public void test() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new MyHandler());
		processExecutor.execute("cmd /c dir");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	class MyHandler extends StreamHandler {
		InputStream is;
		
		@Override
		public void start() {
			is = getProcessInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			try {
				while ((line = reader.readLine()) != null) {
					System.out.println(line);
				}
			}
			catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		@Override
		public void stop() {
		
		}
	}
}
