package io.xunyss.commons.exec;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.xunyss.commons.io.IOUtils;

/**
 *
 * @author XUNYSS
 */
public class PumpStreamHandler extends StreamHandler {
	
	// handler streams
	private OutputStream outputStream = null;
	private OutputStream errorStream = null;
	private InputStream inputStream = null;
	
	private boolean autoCloseStreams = true;
	
	// threads for handle stream
	private Thread outputThread;
	private Thread errorThread;
	private Thread inputThread;
	
	
	public PumpStreamHandler(OutputStream outputStream, OutputStream errorStream, InputStream inputStream) {
		this.outputStream = outputStream;
		this.errorStream = errorStream;
		this.inputStream = inputStream;
	}
	
	public PumpStreamHandler(OutputStream outputStream, OutputStream errorStream) {
		this(outputStream, errorStream, null);
	}
	
	public PumpStreamHandler(OutputStream outputStream) {
		this(outputStream, null);
	}
	
	public void setAutoCloseStreams(boolean autoCloseStreams) {
		this.autoCloseStreams = autoCloseStreams;
	}
	
	@Override
	public void start() {
		if (outputStream != null) {
			outputThread = startPumpThread(getProcessInputStream(), outputStream);
		}
		if (errorStream != null) {
			errorThread = startPumpThread(getProcessErrorStream(), errorStream);
		}
		if (inputStream != null) {
			inputThread = startPumpThread(inputStream, getProcessOutputStream());
		}
	}
	
	@Override
	public void stop() {
		//------------------------------------------------------------------------------------------
		// 이 3개의 thread 에 interrupt 걸어도 thread 에서는 InterruptedException 발생하지 않을 것임
		//------------------------------------------------------------------------------------------
		if (outputStream != null) {
			outputThread.interrupt();
		}
		if (errorStream != null) {
			errorThread.interrupt();
		}
		if (inputStream != null) {
			inputThread.interrupt();
		}
		//------------------------------------------------------------------------------------------
		
		if (autoCloseStreams) {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(errorStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	
	private Thread startPumpThread(InputStream inputStream, OutputStream outputStream) {
		Thread pump = new Thread(new StreamPumper(inputStream, outputStream), "Process Stream Pumper");
		pump.setDaemon(true);
		pump.start();
		return pump;
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * @author XUNYSS
	 */
	private class StreamPumper implements Runnable {
		
		private InputStream inputStream;
		private OutputStream outputStream;
		
		public StreamPumper(InputStream inputStream, OutputStream outputStream) {
			this.inputStream = inputStream;
			this.outputStream = outputStream;
		}
		
		@Override
		public void run() {
			try {
				IOUtils.copy(inputStream, outputStream);
			}
			catch (IOException ex) {
				// TODO: 에러처리
			}
			finally {
				// TODO: 결과처리
			}
		}
	}
}
