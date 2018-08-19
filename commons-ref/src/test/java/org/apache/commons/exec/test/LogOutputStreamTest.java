package org.apache.commons.exec.test;

import java.io.OutputStream;

import org.apache.commons.exec.LogOutputStream;
import org.junit.Test;

public class LogOutputStreamTest {
	
	@Test
	public void test2() throws Exception {
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
			"11111\r\n" +
			"2222";
}
