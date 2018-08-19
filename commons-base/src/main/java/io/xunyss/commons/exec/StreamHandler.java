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
	
	
	void setProcessInputStream(InputStream processInputStream) {
		this.processInputStream = processInputStream;
	}
	
	void setProcessErrorStream(InputStream processErrorStream) {
		this.processErrorStream = processErrorStream;
	}
	
	void setProcessOutputStream(OutputStream processOutputStream) {
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
	 * Start.
	 * RuntimeException 이 던져지지 않도록 구현해야 함 => TODO: internalStart 메소드 이용
	 */
	public abstract void start();
	
	/**
	 * Stop.
	 * RuntimeException 이 던져지지 않도록 구현해야 함 => TODO: internalStop 메소드 이용
	 */
	public abstract void stop();
}
