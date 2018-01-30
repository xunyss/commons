package io.xunyss.commons.exec;

import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author XUNYSS
 */
public abstract class StreamHandler {
	
	// process streams
	private InputStream processInputStream = null;
	private InputStream processErrorStream = null;
	private OutputStream processOutputStream = null;
	
	
	/* default */ void setProcessInputStream(InputStream processInputStream) {
		this.processInputStream = processInputStream;
	}
	
	/* default */ void setProcessErrorStream(InputStream processErrorStream) {
		this.processErrorStream = processErrorStream;
	}
	
	/* default */ void setProcessOutputStream(OutputStream processOutputStream) {
		this.processOutputStream = processOutputStream;
	}
	
	protected InputStream getProcessInputStream() {
		return processInputStream;
	}
	
	protected InputStream getProcessErrorStream() {
		return processErrorStream;
	}
	
	protected OutputStream getProcessOutputStream() {
		return processOutputStream;
	}
	
	
	/**
	 *
	 */
	public abstract void start();
	
	/**
	 *
	 */
	public abstract void stop();
}
