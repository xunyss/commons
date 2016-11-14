package org.xunyss.commons.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author XUNYSS
 */
public class NullOutputStream extends OutputStream {
	
	public static NullOutputStream NULL_OUTPUT_STREAM = new NullOutputStream();
	
	@Override
	public void write(int b) throws IOException {

	}
	
	@Override
	public void write(byte[] b) throws IOException {
		
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		
	}
}
