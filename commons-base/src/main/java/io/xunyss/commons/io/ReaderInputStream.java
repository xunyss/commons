package io.xunyss.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.CharsetEncoder;

/**
 * InputStream that transforms a character stream to a byte stream.
 * 
 * @author XUNYSS
 */
public class ReaderInputStream extends InputStream {
	
	/**
	 *
	 */
	private final Reader reader;
	
	private CharsetEncoder charsetEncoder = null;
	
	
	/**
	 *
	 * @param reader
	 */
	public ReaderInputStream(final Reader reader) {
		this.reader = reader;
	}
	
	/**
	 *
	 * @return
	 * @throws IOException
	 */
	@Override
	public int read() throws IOException {
		return reader.read();
	}
	
	/**
	 *
	 * @param n
	 * @return
	 * @throws IOException
	 */
	@Override
	public long skip(long n) throws IOException {
		return reader.skip(n);
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		reader.close();
	}
	
	/**
	 *
	 * @param readlimit
	 */
	@Override
	public synchronized void mark(int readlimit) {
		try {
			reader.mark(readlimit);
		}
		catch (IOException ignored) {
			/* ignore exception */
		}
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public synchronized void reset() throws IOException {
		reader.reset();
	}
	
	/**
	 *
	 * @return
	 */
	@Override
	public boolean markSupported() {
		return reader.markSupported();
	}
}
