package io.xunyss.commons.lang;

/**
 * Thread utilities.
 *
 * @author XUNYSS
 */
public final class ThreadUtils {
	
	/**
	 * Constructor.
	 */
	private ThreadUtils() {
		// cannot create instance
	}
	
	/**
	 *
	 * @param millis
	 * @throws RuntimeException
	 */
	public static void sleep(long millis) throws RuntimeException {
		try {
			Thread.sleep(millis);
		}
		catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 *
	 * @param millis
	 * @param nanos
	 * @throws RuntimeException
	 */
	public static void sleep(long millis, int nanos) throws RuntimeException {
		try {
			Thread.sleep(millis, nanos);
		}
		catch (InterruptedException ex) {
			throw new RuntimeException(ex);
		}
	}
}
