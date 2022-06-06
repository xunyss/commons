package io.xunyss.commons.exec.support;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.lang.SystemUtils;
import org.junit.Assert;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Unit tests for the ToStringStreamHandler class.
 * 
 * @author XUNYSS
 */
public class ToStringStreamHandlerTest {
	
//	@Test
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

//	@Test
	public void multiThread() {
		final String[] command = SystemUtils.IS_OS_WINDOWS
				? new String[] {"cmd", "/c", "echo hello_world!"}
				: new String[] {"/bin/sh", "-c", "pwd"};

		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						ToStringStreamHandler toStringStreamHandler = new ToStringStreamHandler();
						ProcessExecutor processExecutor = new ProcessExecutor();
						processExecutor.setStreamHandler(toStringStreamHandler);
						processExecutor.execute(command);
						System.out.println("result: " + toStringStreamHandler.getOutputString());
					}
					catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		executorService.shutdown();
	}

	public static void main(String[] args) {
		new ToStringStreamHandlerTest().multiThread();
	}
}
