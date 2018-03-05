package io.xunyss.commons.exec.support;

import io.xunyss.commons.exec.Watchdog;

/**
 *
 * @author XUNYSS
 */
public class TimeoutWatchdog extends Watchdog {
	
	private long timeout;
	private long startTime;
	
	
	public TimeoutWatchdog(long timeout) {
		this.timeout = timeout;
	}
	
	@Override
	protected void start() {
		startTime = System.currentTimeMillis();
		
		new Thread() {
			@Override
			public void run() {
				long timeLeft;
				boolean isWaiting;
				
				synchronized (this) {
					timeLeft = timeout - (System.currentTimeMillis() - startTime);
					isWaiting = timeLeft > 0;
					
					// FIXME: ensureStarted 개념 추가되면 isProcessRunning() 을 대체할 놈을 찾아야 할 것
					// FIXME: stop() 메소드는 외부에서 호출 될 수 있다는 것을 감안 할 것
					while (isProcessRunning() && isWaiting) {
						try {
							// waiting for timeout
							wait(timeLeft);
						}
						catch (InterruptedException ex) {
							// ignore exception
						}
						timeLeft = timeout - (System.currentTimeMillis() - startTime);
						isWaiting = timeLeft > 0;
					}
				}
				
				if (!isWaiting) {
					// fire timeout
					onTimeout();
				}
			}
		}.start();
	}
	
	@Override
	protected void stop() {
		startTime = 0L;
	}
	
	public void onTimeout() {
		// destroy Process
		destroyProcess();
	}
}
