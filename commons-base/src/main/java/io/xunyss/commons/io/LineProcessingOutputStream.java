package io.xunyss.commons.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @author XUNYSS
 */
public abstract class LineProcessingOutputStream extends OutputStream {
	
	private static final char CR = '\r';
	
	private static final char LF = '\n';
	
	
	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	
	private String charsetName = null;
	
	private boolean isPrevCR = false;
	
	
	public LineProcessingOutputStream(final String charsetName) {
		this.charsetName = charsetName;
	}
	
	public LineProcessingOutputStream() {
	
	}
	
	
	@Override
	public void write(final int b) throws IOException {
		// Line Separators
		//   Windows     - CR+LF
		//   Linux       - LF
		//   Classic Mac - CR
		if (b == CR) {
			processBuffer();
		}
		else if (b == LF) {
			if (!isPrevCR) {
				processBuffer();
			}
		}
		else {
			buffer.write(b);
		}
		isPrevCR = b == CR;
	}
	
//	@Override
//	public void write(byte[] b) throws IOException {
//		write(b, 0, b.length);
//	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		final int limit = off + len;	// last offset + 1
		int head = off;					// start offset of line

		while (off < limit) {
			while (off < limit && (b[off] != CR && b[off] != LF)) {
				off++;
			}
			if (off > head) {
				buffer.write(b, head, off - head);
				isPrevCR = false;
			}
			while (off < limit && (b[off] == CR || b[off] == LF)) {
				write(b[off]);
				off++;
			}
			head = off;
		}
	}
	
	@Override
	public void flush() throws IOException {
		if (buffer.size() > 0) {
			processBuffer();
		}
	}
	
	@Override
	public void close() throws IOException {
		flush();
		buffer.close();
	}
	
	
	protected void processBuffer() throws UnsupportedEncodingException {
		String decodedLine = charsetName == null ? buffer.toString() : buffer.toString(charsetName);
		processLine(decodedLine);
		buffer.reset();
	}
	
	
	/**
	 *
	 * @param line
	 */
	protected abstract void processLine(String line);
}
