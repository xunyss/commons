package io.xunyss.commons.exec;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

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
	
//	@Ignore
	@Test
	public void pumpToByteArrayOutputStream() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(byteArrayOutputStream));
		processExecutor.execute("cmd /c dir");
		
		byteArrayOutputStream.close();
		System.out.println(byteArrayOutputStream.toString("MS949"));
	}
	
//	@Ignore
	@Test
	public void pumpToStringWriter() throws Exception {
		StringWriter stringWriter = new StringWriter();
		WriterOutputStream writerOutputStream = new WriterOutputStream(stringWriter, Charset.forName("MS949"));
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new PumpStreamHandler(writerOutputStream));
		processExecutor.execute("cmd /c dir");
		
		writerOutputStream.close();
		System.out.println(stringWriter.toString());
	}
	
	
	@Test
	public void ch() throws Exception {
		
		String s = "송정헌1";
		byte[] b = s.getBytes("MS949");
		
		ByteBuffer in = ByteBuffer.allocate(10);
		CharBuffer out;
		
		CharsetDecoder cd = Charset.forName("MS949")
				.newDecoder()
				.onMalformedInput(CodingErrorAction.REPLACE)
				.onUnmappableCharacter(CodingErrorAction.REPLACE)
				.replaceWith("?");
		
		in.put(b);
		System.out.println(in.position() + " " + in.limit() + " " + in.capacity());
		
		in.flip();
		System.out.println(in.position() + " " + in.limit() + " " + in.capacity());
		
		out = cd.decode(in);
		System.out.println(in.position() + " " + in.limit() + " " + in.capacity());
		
		in.compact();
		System.out.println(in.position() + " " + in.limit() + " " + in.capacity());
		
		System.out.println();
		System.out.println(out.toString());
		System.out.println(out.position() + " " + out.limit() + " " + out.capacity());
	}
}
