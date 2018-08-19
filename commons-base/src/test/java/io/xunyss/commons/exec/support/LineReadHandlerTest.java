package io.xunyss.commons.exec.support;

import org.junit.Test;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.PumpStreamHandler;
import io.xunyss.commons.io.LineProcessingOutputStream;

/**
 * Unit tests for the LineReadHandler class.
 * 
 * @author XUNYSS
 */
public class LineReadHandlerTest {
	
	@Test
	public void test() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(new LineProcessingOutputStream("MS949") {
			@Override
			protected void processLine(String line) {
				System.out.println("@" + line);
			}
		}));

		processExecutor.execute("cmd /c dir");
		
		System.out.println("@");
//		System.out.println((int)'\r');
//		System.out.println((int)'\n');
//
	}
}
