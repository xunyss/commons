package org.apache.commons.exec.test;

import java.io.OutputStream;

import org.apache.commons.exec.LogOutputStream;
import org.junit.Test;

public class LogOutputStreamTest {
	
//	@Test
	public void processLine() throws Exception {
		OutputStream outputStream = new LogOutputStream() {
			@Override
			protected void processLine(String line, int level) {
				System.out.println("^" + line);
			}
		};
		outputStream.write(data.getBytes());
		outputStream.close();
	}
	
	private static final String data =
			"12345\r" +
			"54321\r" +
			"67890\n" +
			"HELLO\r\n" +
			"WORLD";
}
