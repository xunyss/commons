package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public abstract class Watchdog {
	
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
	
	
	/**
	 * Start.
	 * 가급적 스레드를 생성해서 시작하는 방식으로 구현
	 * RuntimeException 이 던져지지 않도록 구현해야 함
	 */
	abstract protected void start();
	
	/**
	 * Stop. (외부 로직에서 호출될 수 있음)
	 * Watchdog 객체를 초기화 하는 로직 구현
	 * RuntimeException 이 던져지지 않도록 구현해야 함
	 */
	abstract protected void stop();
}
