package io.xunyss.commons.exec.stream;

import java.io.IOException;

import io.xunyss.commons.exec.ProcessExecutor;
import org.junit.Ignore;
import org.junit.Test;

public class StringOutputHandlerTest {
	
	@Ignore
	@Test
	public void pumpToStringOutputHandler() throws IOException {
		StringOutputHandler stringOutputHandler = new StringOutputHandler();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(stringOutputHandler);
		
		processExecutor.execute("cmd /c dir");
		String out = stringOutputHandler.getOutputString("MS949");
		System.out.println(out);
		
		processExecutor.execute("ipconfig");
		String out2 = stringOutputHandler.getOutputString("MS949");
		System.out.println(out2);
	}
}
