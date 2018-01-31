package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public interface ResultHandler {
	
	/**
	 * 
	 * @param exitValue
	 */
	void onProcessComplete(int exitValue);
	
	/**
	 * 
	 * @param ex
	 */
	void onProcessFailed(ExecuteException ex);
}
