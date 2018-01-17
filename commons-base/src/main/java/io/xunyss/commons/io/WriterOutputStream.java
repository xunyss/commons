package io.xunyss.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

/**
 *
 * @author XUNYSS
 */
public class WriterOutputStream extends OutputStream {
	
	/**
	 *
	 */
	private final Writer writer;
	
	
	/**
	 *
	 * @param writer
	 */
	public WriterOutputStream(final Writer writer) {
		this.writer = writer;
	}
	
	/**
	 *
	 * @param b
	 * @throws IOException
	 */
	@Override
	public void write(int b) throws IOException {
		writer.write(b);
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void flush() throws IOException {
		writer.flush();
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		writer.close();
	}
}
