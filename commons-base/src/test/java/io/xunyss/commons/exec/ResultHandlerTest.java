package io.xunyss.commons.exec;

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
	
	// for multi thread test
	public static void main(String[] args) throws ExecuteException {
		ResultHandlerTest test = new ResultHandlerTest();
		test.executeWinAppWithResultHandlerAsync();
//		test.executeWinAppWithResultHandlerSync();
		
		System.out.println("main method finished");
	}
}
