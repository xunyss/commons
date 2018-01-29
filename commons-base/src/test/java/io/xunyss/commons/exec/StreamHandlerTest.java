package io.xunyss.commons.exec;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.junit.Ignore;
import org.junit.Test;

import io.xunyss.commons.io.WriterOutputStream;

/**
 * Unit tests for the StreamHandler class.
 *
 * @author XUNYSS
 */
public class StreamHandlerTest {
	
	@Ignore
	@Test
	public void streamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new StreamHandler() {
			 @Override
			 public void start() {
				 System.out.println("start handle stream");
			 }
			 @Override
			 public void stop() {
				 System.out.println("stop handle stream");
			 }
		 });
		 processExecutor.execute("cmd /c dir");
	}
	
	@Ignore
	@Test
	public void pumpStreamHandler() throws ExecuteException {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler());
		processExecutor.execute("cmd /c dir");
	}
	
	@Ignore
	@Test
	public void pumpToByteArrayOutputStream() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));
		processExecutor.execute("cmd /c dir");
		
		byteArrayOutputStream.close();
		System.out.println(byteArrayOutputStream.toString("MS949"));
	}
	
	@Ignore
	@Test
	public void pumpToStringWriter() throws Exception {
		StringWriter stringWriter = new StringWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(stringWriter);
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(writerOutputStream));
		processExecutor.execute("cmd /c dir");
		
		writerOutputStream.close();
		System.out.println(stringWriter.toString());
	}
	
	@Test
	public void ch() throws Exception {
	
		String s = "송정헌";
		byte[] b = s.getBytes("MS949");
		System.out.println(b.length);
		
		ByteBuffer in = ByteBuffer.wrap(b);
		CharBuffer out;

		CharsetDecoder cd = Charset.forName("MS949").newDecoder();
		out = cd.decode(in);
		
		String ss = out.toString();
		System.out.println(ss);
	}
}
