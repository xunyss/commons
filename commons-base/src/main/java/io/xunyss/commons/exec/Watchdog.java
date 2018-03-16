package io.xunyss.commons.exec;

import io.xunyss.commons.lang.ArrayUtils;

/**
 *
 * @author XUNYSS
 */
public abstract class Watchdog {
	
	/**
	 *
	 */
	private enum STATUS {
		READY,
		STARTED,
		STOPPED,
		ERROR
	}
	
	
	/**
	 *
	 */
	private Process process = null;
	private String[] commands = null;
	
	/**
	 *
	 */
	private volatile STATUS status = STATUS.READY;
	
	
	/**
	 *
	 */
	void ready() {
		process = null;
		status = STATUS.READY;
	}
	
	/**
	 *
	 * @param executeException
	 */
	void errorMonitoring(ExecuteException executeException) {
		this.status = STATUS.ERROR;
		
		synchronized (this) {
			notifyAll();
		}
	}
	
	/**
	 *
	 * @param process
	 * @param commands
	 */
	void startMonitoring(Process process, String[] commands) {
		/*
		 * 2018.03.16 XUNYSS
		 * add argument "commands" to identify process
		 */
		this.process = process;
		this.commands = commands;
		this.status = STATUS.STARTED;
		
		synchronized (this) {
			notifyAll();
		}
		start();
	}
	
	/**
	 *
	 */
	void stopMonitoring() {
		this.process = null;
		this.status = STATUS.STOPPED;
		
		stop();
	}
	
	
	/**
	 *
	 * @return
	 */
	protected synchronized boolean isProcessRunning() {
		// Multi-Thread 환경에서 ProcessExecutor 가 Process 객체를 생성하기 전에
		// 정확히는 startMonitoring() 메소드가 호출되기 전에
		// isProcessRunning() 메소드가 실행 된다면 false 를 리턴할 수 있음
		// isProcessRunning() 메소드가 '실행중이면 종료하겠다' 라는 의미의 구현을 위해 사용 된다면 오류 발생
		// 즉, 프로세스의 실행여부를 확인하기 위해 process 객체가 실제로 생성될 때까지 대기해야 할 필요 있음
//		return processRunning;
		
		// 2018.03.11 XUNYSS
		// processRunning boolean 변수 1개 에서, STATUS enum 변수로 운용
		// 메소드에 synchronized 구문 추가
		ensureStarted();	// waiting until STATUS.STARTED
		return status == STATUS.STARTED;
	}
	
	/**
	 *
	 */
	protected synchronized void destroyProcess() {
		ensureStarted();	// waiting until STATUS.STARTED
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
	
	private void ensureStarted() {
		while (status == STATUS.READY) {
			try {
				wait();
			}
			catch (InterruptedException ex) {
				throw new RuntimeException(ex);
			}
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
	
	
	/**
	 *
	 * @return
	 */
	public String getProcessCommands() {
		return ArrayUtils.toString(commands);
	}
}
