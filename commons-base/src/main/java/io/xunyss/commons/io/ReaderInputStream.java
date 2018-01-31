package io.xunyss.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;

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
	private CharBuffer charBuffer = null;
	private ByteBuffer byteBuffer = null;
	
	
	public ReaderInputStream(final Reader reader, final Charset charset) {
		this.reader = reader;
		if (charset != null) {
			this.charsetEncoder = charset.newEncoder()
					.onMalformedInput(CodingErrorAction.REPLACE)
					.onUnmappableCharacter(CodingErrorAction.REPLACE);
		}
	}
	
	public ReaderInputStream(final Reader reader, final String charsetName) {
		this(reader, Charset.forName(charsetName));
	}
	
	/**
	 *
	 * @param reader
	 */
	public ReaderInputStream(final Reader reader) {
		this(reader, (Charset) null);
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
