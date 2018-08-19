package io.xunyss.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;

/**
 * OutputStream that transforms a byte stream to a character stream.
 *
 * @author XUNYSS
 */
public class WriterOutputStream extends OutputStream {
	
	/**
	 *
	 */
	private static final int BYTE_BUFFER_CAPACITY = 512;
	private static final int CHAR_BUFFER_CAPACITY = 1024;
	
	/**
	 *
	 */
	private final Writer writer;
	
	private CharsetDecoder charsetDecoder = null;
	private ByteBuffer byteBuffer = null;
	private CharBuffer charBuffer = null;
	
	private boolean isLastFlush = false;
	
	
	/**
	 *
	 * @param writer
	 * @param charset
	 */
	public WriterOutputStream(final Writer writer, final Charset charset) {
		this.writer = writer;
		if (charset != null) {
			this.charsetDecoder = charset.newDecoder()
					.onMalformedInput(CodingErrorAction.REPLACE)
					.onUnmappableCharacter(CodingErrorAction.REPLACE)
					.replaceWith("?");
			this.byteBuffer = ByteBuffer.allocate(BYTE_BUFFER_CAPACITY);
			this.charBuffer = CharBuffer.allocate(CHAR_BUFFER_CAPACITY);
//			this.isLastFlush = false;
		}
	}
	
	/**
	 *
	 * @param writer
	 * @param charsetName
	 */
	public WriterOutputStream(final Writer writer, final String charsetName) {
		this(writer, Charset.forName(charsetName));
	}
	
	/**
	 *
	 * @param writer
	 */
	public WriterOutputStream(final Writer writer) {
		this(writer, (Charset) null);
	}
	
	/**
	 *
	 * @param b
	 * @throws IOException
	 */
	@Override
	public void write(int b) throws IOException {
		if (charsetDecoder != null && byteBuffer != null) {
			if (byteBuffer.hasRemaining()) {
				byteBuffer.put((byte) b);
			}
			if (!byteBuffer.hasRemaining()) {
				flush();
			}
		}
		else {
			writer.write(b);
		}
	}
	
	/**
	 *
	 * @param b
	 * @throws IOException
	 */
	@Override
	public void write(byte[] b) throws IOException {
		// TODO: implement this method
		super.write(b);
	}
	
	/**
	 *
	 * @param b
	 * @param off
	 * @param len
	 * @throws IOException
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		// TODO: implement this method
		super.write(b, off, len);
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void flush() throws IOException {
		if (charsetDecoder != null && byteBuffer != null) {
			// byteBuffer 의 capacity 가 stream 의 전체 사이즈 보다 클때만 정상 동작
//			byteBuffer.flip();
//			CharBuffer charBuffer = charsetDecoder.decode(byteBuffer);
//			writer.write(charBuffer.array(), 0, charBuffer.limit());
//			writer.flush();
//			byteBuffer.compact();
			
			// flip input byte buffer
			byteBuffer.flip();
			
			CoderResult coderResult;
			while (byteBuffer.hasRemaining()) {
				// endOfInput 값이 true 일 경우 byteBuffer 끝까지 decode 를 시도 함
				// endofInput 값이 false 일 경우 byteBuffer decode 가능한 지점까지 decode 함
				// endOfInput == false 일때의 byteBuffer.position() 값은
				// endOfInput == true 일때의 byteBuffer.position() 값보다 작거나 같다.
				coderResult = charsetDecoder.decode(byteBuffer, charBuffer, isLastFlush);
				// 정상
				if (coderResult.isOverflow() || coderResult.isUnderflow()) {
					// write to writer
					if (charBuffer.position() > 0) {
						writer.write(charBuffer.array(), 0, charBuffer.position());
						writer.flush();
						charBuffer.rewind();
					}
					// 출력버퍼(charBuffer) 가 다 참
					if (coderResult.isOverflow()) {
						continue;
					}
					// 디코딩 처리 완료
					if (coderResult.isUnderflow()) {
						break;
					}
				}
				// 그외
				else {
					throw new IOException("Unexpected CoderResult: " + coderResult);
				}
			}
			// compact input byte buffer
			byteBuffer.compact();
		}
		else {
			writer.flush();
		}
	}
	
	/**
	 *
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		if (charsetDecoder != null && byteBuffer != null) {
			isLastFlush = true;
			flush();
		}
		writer.close();
	}
}
