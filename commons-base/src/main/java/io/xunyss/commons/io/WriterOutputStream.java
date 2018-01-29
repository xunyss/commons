package io.xunyss.commons.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

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
//		writer.write(b);
		
		if (buff < 729) {
			in.put((byte) b);
			buff++;
		}
		if (buff >= 729) {
			buff = 0;
			in.flip();
			CharBuffer out = cd.decode(in);
			writer.write(out.array());
			writer.flush();
			in.compact();
		}
	}
	CharsetDecoder cd = Charset.forName("MS949").newDecoder();
	ByteBuffer in = ByteBuffer.allocate(729);
	int buff=0;
	
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
