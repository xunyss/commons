package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public abstract class WatchDog {
	
	private Process process = null;
	private boolean processRunning = false;
	
	void startMonitoring(Process process) {
		this.process = process;
		this.processRunning = true;
		start();
	}
	
	void stopMonitoring() {
		this.process = null;
		this.processRunning = false;
		stop();
	}
	
	
	protected boolean isProcessRunning() {
		return processRunning;
	}
	
	protected void destroyProcess() {
		try {
			// check if the process was not stopped
			if (process != null) {
				process.exitValue();
			}
		}
		catch (IllegalThreadStateException ex) {
			process.destroy();
		}
	}
	
	
	abstract protected void start();
	
	abstract protected void stop();
}
