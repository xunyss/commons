package io.xunyss.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for the ReaderInputStream class.
 *
 * @author XUNYSS
 */
public class ReaderInputStreamTest {
	
	private void read(String data, String charset) throws IOException {
		StringReader reader = new StringReader(data);
		ReaderInputStream inputStream = new ReaderInputStream(reader, charset);
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		int copyBytes = IOUtils.copy(inputStream, outputStream);
		IOUtils.closeQuietly(outputStream);
		IOUtils.closeQuietly(reader);
		
		Assert.assertEquals(data.getBytes(charset).length, copyBytes);
		Assert.assertEquals(data, outputStream.toString(charset));
	}
	
	@Test
	public void readMS949() throws IOException {
		read("송정헌", "MS949");
	}
	
	@Test
	public void readUTF8() throws IOException {
		read("송정헌", "UTF8");
	}
}
