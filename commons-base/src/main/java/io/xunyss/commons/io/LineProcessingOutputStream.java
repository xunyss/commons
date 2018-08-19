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
	
	private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	
	private String charsetName = null;
	
	private boolean preCr = false;
	
	
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
		if (b == '\r') {
			processBuffer();
		}
		else if (b == '\n') {
			if (!preCr) {
				processBuffer();
			}
		}
		else {
			buffer.write(b);
		}
		preCr = b == '\r';
	}
	
	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}
	
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
//		super.write(b, off, len);
//		buffer.write(b, off, len);
		// TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		// TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		// TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		int begin = off;
		int size = len;
		int cpos;

		for (int i = 0; i < len; i++) {
			cpos = off + i;
			size = cpos - begin + 1;
			
			if (b[cpos] == '\r' || b[cpos] == '\n') {
				buffer.write(b, begin, size - 1);
				write(b[cpos]);

				begin = cpos + 1;
			}
		}
		buffer.write(b, begin, size - 1);
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
	protected abstract void processLine(final String line);
}
