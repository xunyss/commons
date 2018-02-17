package io.xunyss.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the WriterOutputStream class.
 *
 * @author XUNYSS
 */
public class WriterOutputStreamTest {
	
	private void write(String data, String charset) throws IOException {
		byte[] inputData = data.getBytes(charset);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(inputData);
		
		StringWriter writer = new StringWriter();
		WriterOutputStream outputStream = new WriterOutputStream(writer, charset);
		
		int copyBytes = IOUtils.copy(inputStream, outputStream);
		IOUtils.closeQuietly(inputStream);
		IOUtils.closeQuietly(writer);
		
		Assert.assertEquals(inputData.length, copyBytes);
		Assert.assertEquals(data, writer.toString());
	}
	
	@Test
	public void writeMS949() throws IOException {
		write("송정헌", "MS949");
	}
	
	@Test
	public void writeUTF8() throws IOException {
		write("송정헌", "UTF8");
	}
}
