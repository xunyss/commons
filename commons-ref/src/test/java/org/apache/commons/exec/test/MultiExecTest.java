package org.apache.commons.exec.test;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiExecTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		for (int i = 0; i < 100; i++) {
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					try {
						ByteArrayOutputStream output = new ByteArrayOutputStream();
						Executor executor = new DefaultExecutor();
						executor.setStreamHandler(new PumpStreamHandler(output));
						executor.execute(CommandLine.parse("/bin/sh -c pwd"));
						System.out.println("result: " + output);
					}
					catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			});
		}
		executorService.shutdown();
	}
}
