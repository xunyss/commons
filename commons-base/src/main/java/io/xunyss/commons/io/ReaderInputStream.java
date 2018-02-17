package io.xunyss.commons.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
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
	private static final int BYTE_BUFFER_CAPACITY = 512;
	private static final int CHAR_BUFFER_CAPACITY = 1024;
	
	/**
	 *
	 */
	private final Reader reader;
	
	private CharsetEncoder charsetEncoder = null;
	private ByteBuffer byteBuffer = null;
	private CharBuffer charBuffer = null;
	
	private boolean readerEndOfInput = false;
	private CoderResult coderResult = null;
	
	
	public ReaderInputStream(final Reader reader, final Charset charset) {
		this.reader = reader;
		if (charset != null) {
			this.charsetEncoder = charset.newEncoder()
					.onMalformedInput(CodingErrorAction.REPLACE)
					.onUnmappableCharacter(CodingErrorAction.REPLACE);
			this.byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_CAPACITY);
			this.charBuffer = CharBuffer.allocate(CHAR_BUFFER_CAPACITY);
			this.byteBuffer.flip();
			this.charBuffer.flip();
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
		if (charsetEncoder != null && byteBuffer != null) {
			while (true) {
				if (byteBuffer.hasRemaining()) {
					return byteBuffer.get();
				}
				// fill buffer
				fillBuffer();
				if (readerEndOfInput && !byteBuffer.hasRemaining()) {
					return (byte) IOUtils.EOF;
				}
			}
		}
		else {
			return (byte) reader.read();
		}
	}
	
	/**
	 * Fills the internal char buffer from the reader.
	 * 
	 * @throws IOException
	 */
	private void fillBuffer() throws IOException {
		if (!readerEndOfInput && (coderResult == null || coderResult.isUnderflow())) {
			// compact input char buffer
			charBuffer.compact();
			
			// using char-array read instead CharBuffer read
			// because it is more efficient 
//			int readLength = reader.read(charBuffer);
			int readLength = reader.read(charBuffer.array(), charBuffer.position(), charBuffer.remaining());
			if (readLength == IOUtils.EOF) {
				readerEndOfInput = true;
			}
			else {
				// set char buffer position
				charBuffer.position(charBuffer.position() + readLength);
			}
			
			// flip input char buffer
			charBuffer.flip();
		}
		
		byteBuffer.compact();
		coderResult = charsetEncoder.encode(charBuffer, byteBuffer, readerEndOfInput);
		byteBuffer.flip();
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		if (charsetEncoder != null && byteBuffer != null) {
			//
		}
		reader.close();
	}
}
