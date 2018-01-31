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
	private boolean closeStreams = false;
	
	// threads for handle stream
	private Thread outputThread;
	private Thread errorThread;
	private Thread inputThread;
	private SystemInputPumper systemInputPumper;
	
	
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
	
	public PumpStreamHandler() {
		this(System.out, System.err, null);
	}
	
	
	protected final OutputStream getOutputStream() {
		return outputStream;
	}

	protected final OutputStream getErrorStream() {
		return errorStream;
	}
	
	protected final InputStream getInputStream() {
		return inputStream;
	}
	
	public void setCloseStreams(boolean closeStreams) {
		this.closeStreams = closeStreams;
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
			inputThread = inputStream == System.in
					? startSystemInputPumpThread(getProcessOutputStream())
					: startPumpThread(inputStream, getProcessOutputStream());
		}
		else {
			IOUtils.closeQuietly(getProcessOutputStream());
		}
	}
	
	@Override
	public void stop() {
		if (outputStream != null) {
			outputThread.interrupt();
		}
		
		if (errorStream != null) {
			errorThread.interrupt();
		}
		
		if (inputStream != null) {
			if (systemInputPumper != null) {	// inputStream == System.in
				systemInputPumper.stopPump();
			}
			inputThread.interrupt();
		}
		
		if (closeStreams) {
			IOUtils.closeQuietly(outputStream);
			IOUtils.closeQuietly(errorStream);
			IOUtils.closeQuietly(inputStream);
		}
	}
	
	
	/**
	 *
	 * @param inputStream
	 * @param outputStream
	 * @return
	 */
	private Thread startPumpThread(InputStream inputStream, OutputStream outputStream) {
		Thread pumpThread = new Thread(new StreamPumper(inputStream, outputStream), "Process Stream Pumper");
		pumpThread.setDaemon(true);
		pumpThread.start();
		return pumpThread;
	}
	
	/**
	 *
	 * @param outputStream
	 * @return
	 */
	private Thread startSystemInputPumpThread(OutputStream outputStream) {
		systemInputPumper = new SystemInputPumper(outputStream);
		Thread pumpThread = new Thread(systemInputPumper, "Process System Input Pumper");
		pumpThread.setDaemon(true);
		pumpThread.start();
		return pumpThread;
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * @author XUNYSS
	 */
	private static class StreamPumper implements Runnable {
		
		private static final int PUMP_BUFFER_SIZE = 1024 * 4;
		
		private InputStream inputStream;
		private OutputStream outputStream;
		
		private StreamPumper(InputStream inputStream, OutputStream outputStream) {
			this.inputStream = inputStream;
			this.outputStream = outputStream;
		}
		
		@Override
		public void run() {
			try {
//				IOUtils.copy(inputStream, outputStream);
				
				// pump stream
				final byte[] buffer = new byte[PUMP_BUFFER_SIZE];
				int readLength;
				while ((readLength = inputStream.read(buffer)) > IOUtils.EOF) {
					outputStream.write(buffer, 0, readLength);
					
					// ProcessExecutor 클래스에서 process.waitFor() 메소드 이후 PumpStreamHandler.stop() 메소드가 수행 됨
					// stream I/O pump 가 모두 수행되기 전까지는 이 부분이 실행되는 상황은 발생하지 않을 것임 (내생각)
					if (Thread.interrupted()) {
						break;
					}
				}
			}
			catch (IOException ex) {
				// do nothing
			}
			finally {
				try {
					outputStream.flush();
				}
				catch (IOException ex) {
					// do nothing
				}
			}
		}
	}
	
	/**
	 * @author XUNYSS
	 */
	private static class SystemInputPumper implements Runnable {
		
		private static final int SLEEPING_TIME = 100;
		
		private OutputStream outputStream;
		private volatile boolean stop;
		
		private SystemInputPumper(OutputStream outputStream) {
			this.outputStream = outputStream;
			this.stop = false;
		}
		
		@Override
		public void run() {
			final InputStream stdin = System.in;	// System Standard Input
			try {
				while (!stop) {
					while (stdin.available() > 0 && !stop) {
						outputStream.write(stdin.read());
					}
					outputStream.flush();
					Thread.sleep(SLEEPING_TIME);
				}
			}
			catch (IOException | InterruptedException ex) {
				// do nothing
			}
		}
		
		private void stopPump() {
			stop = true;
		}
	}
}
