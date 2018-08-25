package io.xunyss.commons.io;

import java.io.OutputStream;

import org.junit.Test;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.PumpStreamHandler;

public class LineProcessingOutputStreamTest {
	
	@Test
	public void processDirResult() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(new LineProcessingOutputStream("MS949") {
			@Override
			protected void processLine(String line) {
				System.out.println("@" + line);
			}
		}));
		
		processExecutor.execute("cmd /c dir");
		System.out.println("@");
	}
	
	@Test
	public void processLine() throws Exception {
		OutputStream outputStream = new LineProcessingOutputStream("MS949") {
			@Override
			protected void processLine(String line) {
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
