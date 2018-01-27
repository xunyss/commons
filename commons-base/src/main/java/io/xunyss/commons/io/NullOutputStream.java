package io.xunyss.commons.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author XUNYSS
 */
public final class NullOutputStream extends OutputStream {
	
	/**
	 *
	 */
	public static final NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();
	
	
	/**
	 *
	 * @param b
	 * @throws IOException
	 */
	@Override
	public void write(int b) throws IOException {
		// do nothing
	}
	
	/**
	 *
	 * @param b
	 * @throws IOException
	 */
	@Override
	public void write(byte[] b) throws IOException {
		// do nothing
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
		// do nothing
	}
}
