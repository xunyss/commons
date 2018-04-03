package io.xunyss.commons.exec.support;

import io.xunyss.commons.exec.Watchdog;

/**
 *
 * @author XUNYSS
 */
public class TimeoutWatchdog extends Watchdog {
	
	private long timeout;
	private long startTime;
	private boolean isWatching;
	
	private final Object thisWatchdog;	// FIXME: 얘를 왜 만들었지?
	
	
	public TimeoutWatchdog(long timeout) {
		this.timeout = timeout;
		this.isWatching = true;
		this.thisWatchdog = this;
	}
	
	@Override
	protected void start() {
		startTime = System.currentTimeMillis();
		
		new Thread("Timeout-Watchdog") {
			@Override
			public void run() {
				long timeLeft;
				boolean isWaiting;
				
				synchronized (thisWatchdog) {
					timeLeft = timeout - (System.currentTimeMillis() - startTime);
					isWaiting = timeLeft > 0;
					
					while (isWatching && isWaiting) {
						try {
							// waiting for timeout
							thisWatchdog.wait(timeLeft);
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
		synchronized (thisWatchdog) {
			isWatching = false;
			thisWatchdog.notifyAll();
		}
	}
	
	public void cancel() {
		stop();
	}
	
	public void onTimeout() {
		// destroy Process
		destroyProcess();
	}
}
