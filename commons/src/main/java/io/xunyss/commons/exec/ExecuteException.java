package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public class ExecuteException extends Exception {
	
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
