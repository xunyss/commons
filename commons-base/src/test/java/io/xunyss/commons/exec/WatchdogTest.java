package io.xunyss.commons.exec;

import java.io.IOException;
import java.util.Date;

/**
 *
 * @author XUNYSS
 */
public class WatchdogTest {
	
	public void isProcessRunning(String command) throws IOException {
		final Watchdog watchdog = new Watchdog() {
			@Override
			protected void start() {
				System.out.println("watchdog started: " + new Date());
			}
			
			@Override
			protected void stop() {
				System.out.println("watchdog stopped:" + new Date());
			}
			
			@Override
			public boolean isProcessRunning() {
				return super.isProcessRunning();
			}
		};
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setWatchdog(watchdog);
		processExecutor.execute(command, new ResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				System.out.println("onProcessComplete: " + exitValue);
				System.out.println("callback isProcessRunning: " + watchdog.isProcessRunning());
			}
			
			@Override
			public void onProcessFailed(ExecuteException ex) {
				System.out.println("onProcessFailed: " + ex.toString());
				System.out.println("callback isProcessRunning: " + watchdog.isProcessRunning());
			}
		});
		
		System.out.println("isProcessRunning: " + watchdog.isProcessRunning());
	}
	
	
	// for multi thread test
	public static void main(String[] args) throws IOException {
		WatchdogTest test = new WatchdogTest();
		test.isProcessRunning("notepad.exe");		// normal
		test.isProcessRunning("notepad1.exe");		// error
	}
}
