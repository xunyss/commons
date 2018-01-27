package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
@SuppressWarnings("serial")
public class ExecuteException extends RuntimeException {
	
	/**
	 *
	 */
	private int exitValue;
	
	
	/**
	 *
	 * @return
	 */
	public int getExitValue() {
		return exitValue;
	}
}
