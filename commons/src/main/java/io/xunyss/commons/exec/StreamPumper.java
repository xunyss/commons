package io.xunyss.commons.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.xunyss.commons.io.IOUtils;

/**
 * 
 * @author XUNYSS
 */
public class StreamPumper implements Runnable {
	
	private InputStream in;
	private OutputStream out;
	
	public StreamPumper(InputStream in, OutputStream out) {
		this.in = in;
		this.out = out;
	}

	@Override
	public void run() {
		try {
			IOUtils.copy(in, out);
		}
		catch (IOException ioe) {
			// TODO: 에러처리
			throw new ExecuteException();
		}
	}
}
