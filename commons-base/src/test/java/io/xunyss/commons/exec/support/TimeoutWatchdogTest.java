package io.xunyss.commons.exec.support;

import java.io.IOException;
import java.util.Date;

import io.xunyss.commons.exec.ExecuteException;
import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.ResultHandler;
import io.xunyss.commons.lang.ThreadUtils;

/**
 *
 * @author XUNYSS
 */
public class TimeoutWatchdogTest {
	
	public void timeout() throws IOException {
		ProcessExecutor processExecutor = new ProcessExecutor(true);
		processExecutor.setWatchdog(new TimeoutWatchdog(2000));		// 2초
		
		System.out.println("start: " + new Date());
		processExecutor.execute("notepad.exe");
		System.out.println("end: " + new Date());
	}
	
	public void stopBeforeTimeout() throws IOException {
		ProcessExecutor processExecutor = new ProcessExecutor(true);
		processExecutor.setWatchdog(new TimeoutWatchdog(60000));	// 1분
		
		System.out.println("start: " + new Date());
		processExecutor.execute("notepad.exe");
		System.out.println("end: " + new Date());
	}
	
	public void cancelTimeout() throws IOException {
		TimeoutWatchdog watchdog = new TimeoutWatchdog(3000);
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setWatchdog(watchdog);
		
		System.out.println("start: " + new Date());
		processExecutor.execute("notepad.exe", new ResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				System.out.println("end: " + new Date());
			}
			@Override
			public void onProcessFailed(ExecuteException ex) {
			
			}
		});
		
		ThreadUtils.sleep(1000);
		watchdog.cancel();
	}
	
	
	// for multi thread test
	public static void main(String[] args) throws IOException {
		TimeoutWatchdogTest test = new TimeoutWatchdogTest();
		test.timeout();
//		test.stopBeforeTimeout();
//		test.cancelTimeout();
	}
}
