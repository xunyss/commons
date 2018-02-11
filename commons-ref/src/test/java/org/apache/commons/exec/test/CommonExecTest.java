package org.apache.commons.exec.test;


import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteResultHandler;
import org.apache.commons.exec.Executor;
import org.junit.After;
import org.junit.Test;

public class CommonExecTest {
	
	@After
	public void finish() throws InterruptedException {
		Thread.sleep(10000);
	}
	
	@Test
	public void execAsync() throws Exception {
		Executor executor = new DefaultExecutor();
		executor.execute(CommandLine.parse("notepad.exe"), new ExecuteResultHandler() {
			@Override
			public void onProcessComplete(int exitValue) {
				System.out.println(exitValue);
			}
			
			@Override
			public void onProcessFailed(ExecuteException e) {
				e.printStackTrace();
			}
		});
		System.out.println(22);
	}
}
