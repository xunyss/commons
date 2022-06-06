package io.xunyss.commons.exec;

import io.xunyss.commons.io.WriterOutputStream;
import io.xunyss.commons.lang.SystemUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;

/**
 * Unit tests for the PumpStreamHandler class.
 *
 * @author XUNYSS
 */
public class PumpStreamHandlerTest {
	
//	@Rule
	public final TemporaryFolder tmpDir = new TemporaryFolder();
	
	private String command = "cmd /c dir";
	private String consoleCharset = "MS949";


	@Before
	public void setup() {
		command = SystemUtils.IS_OS_WINDOWS ? "cmd /c dir" : "/bin/sh -c ls -la";
		consoleCharset = SystemUtils.IS_OS_WINDOWS ? "MS949" : "UTF8";
	}
	
	@Ignore
//	@Test
	public void pumpToSystemOutErr() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler());
		processExecutor.execute(command);
	}
	
	@Ignore
//	@Test
	public void pumpToSystemOutErrWithSystemIn() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(System.out, System.err, System.in));
		processExecutor.execute(command);
	}
	
	@Ignore
	@Test
	public void pumpToByteArrayOutputStream() throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));
		processExecutor.execute(command);
		
		byteArrayOutputStream.close();
		System.out.println(byteArrayOutputStream.toString(consoleCharset));
	}
	
	@Ignore
//	@Test
	public void pumpToStringWriter() throws IOException {
		StringWriter stringWriter = new StringWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(stringWriter, consoleCharset);
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(writerOutputStream));
		processExecutor.execute(command);
		
		writerOutputStream.close();
		System.out.println(stringWriter.toString());
	}
	
//	@Test
	public void pumpToFileOutputStreamAndDoNotClose() throws IOException {
		File tempFile = new File(tmpDir.getRoot(), "cannot_delete_me.txt");
		
		PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(new FileOutputStream(tempFile));
		pumpStreamHandler.setCloseStreams(false);
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(pumpStreamHandler);
		processExecutor.execute(command);
		
		Assert.assertFalse(tempFile.delete());
	}
	
//	@Test
	public void pumpToFileOutputStreamAndAutoClose() throws IOException {
		File tempFile = new File(tmpDir.getRoot(), "can_delete_me.txt");
		
		PumpStreamHandler pumpStreamHandler = new PumpStreamHandler(new FileOutputStream(tempFile));
		pumpStreamHandler.setCloseStreams(true);
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(pumpStreamHandler);
		processExecutor.execute(command);
		
		Assert.assertTrue(tempFile.delete());
	}
}
