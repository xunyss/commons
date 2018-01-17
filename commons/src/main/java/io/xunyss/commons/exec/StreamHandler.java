package io.xunyss.commons.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.xunyss.commons.io.IOUtils;

/**
 * TODO: stdout 어찌 처리?
 * TODO: stdin, stderr 스레드로 돌려야 하지 않을까
 * 
 * @author XUNYSS
 */
public class StreamHandler {
	
	private OutputStream outputStream = null;
	private OutputStream errorStream = null;
	private InputStream inputStream = null;
	
	private InputStream processInputStream = null;
	private InputStream processErrorStream = null;
	private OutputStream processOutputStream = null;
	
	private boolean autoCloseStreams = true;
	
	
	public StreamHandler(OutputStream outputStream, OutputStream errorStream, InputStream inputStream) {
		this.outputStream = outputStream;
		this.errorStream = errorStream;
		this.inputStream = inputStream;
	}
	
	public StreamHandler(OutputStream outputStream, OutputStream errorStream) {
		this(outputStream, errorStream, null);
	}
	
	public StreamHandler(OutputStream outputStream) {
		this(outputStream, null);
	}
	
	public StreamHandler() {
		
	}
	
	
//	public void setOutputStream(OutputStream outputStream) {
//		this.outputStream = outputStream;
//	}
//	
//	public void setErrorStream(OutputStream errorStream) {
//		this.errorStream = errorStream;
//	}
//	
//	public void setInputStream(InputStream inputStream) {
//		this.inputStream = inputStream;
//	}
	
	
	protected void setProcessInputStream(InputStream processInputStream) {
		this.processInputStream = processInputStream;
	}
	
	protected void setProcessErrorStream(InputStream processErrorStream) {
		this.processErrorStream = processErrorStream;
	}
	
	protected void setProcessOutputStream(OutputStream processOutputStream) {
		this.processOutputStream = processOutputStream;
	}
	
	public void setAutoCloseStreams(boolean autoCloseStreams) {
		this.autoCloseStreams = autoCloseStreams;
	}
	
	private Thread p1, p2, p3;
	public void start() throws IOException {
		if (outputStream != null) {
			p1 = startPumpThread(processInputStream, outputStream);
		}
		if (errorStream != null) {
			p2 = startPumpThread(processErrorStream, errorStream);
		}
		if (inputStream != null) {
			p3 = startPumpThread(inputStream, processOutputStream);
		}
	}
	
	public void stop() {
		// TODO: implements "catch InterruptedException"
		if (p1 != null) {
			p1.interrupt();
		}
		if (p2 != null) {
			p2.interrupt();
		}
		if (p3 != null) {
			p3.interrupt();
		}
		
		if (autoCloseStreams) {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(errorStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	private Thread startPumpThread(InputStream in, OutputStream out) {
		Thread pump = new Thread(new StreamPumper(in, out));
		pump.setDaemon(true);
		pump.start();
		
		return pump;
	}
}
