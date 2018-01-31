package io.xunyss.commons.exec;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Unit tests for the ResultHandler class.
 *
 * @author XUNYSS
 */
public class ResultHandlerTest {
	
	public void executeWinAppWithResultHandlerAsync() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.execute("notepad.exe", new ResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				System.out.println("notepad process exit value: " + exitValue);
			}
			@Override
			public void onProcessFailed(ExecuteException ex) {
				ex.printStackTrace();
			}
		});
		
		System.out.println("executeWinAppWithResultHandler method finished");
	}
	
	public void executeWinAppWithResultHandlerSync() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor(true);
		processExecutor.execute("notepad.exe", new ResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				System.out.println("notepad process exit value: " + exitValue);
			}
			@Override
			public void onProcessFailed(ExecuteException ex) {
				ex.printStackTrace();
			}
		});
		
		System.out.println("executeWinAppWithResultHandler method finished");
	}
	
	public void executeConsoleAsyncWithStreamHandler() throws IOException {
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));
		processExecutor.execute("cmd /c dir", new ResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				try {
					System.out.println(byteArrayOutputStream.toString("MS949"));
					byteArrayOutputStream.close();
				}
				catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			@Override
			public void onProcessFailed(ExecuteException ex) {
				ex.printStackTrace();
			}
		});
	}
	
	// for multi thread test
	public static void main(String[] args) throws IOException {
		ResultHandlerTest test = new ResultHandlerTest();
//		test.executeWinAppWithResultHandlerAsync();
//		test.executeWinAppWithResultHandlerSync();
		test.executeConsoleAsyncWithStreamHandler();
		
		System.out.println("main method finished");
	}
}
