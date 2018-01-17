package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public class ExecuteException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5786792486475324742L;
	
	
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
