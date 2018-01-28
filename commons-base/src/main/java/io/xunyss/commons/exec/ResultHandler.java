package io.xunyss.commons.exec;

/**
 *
 * @author XUNYSS
 */
public interface ResultHandler {
	
	void onProcessComplete(int exitValue);
	
	void onProcessFailed(ExecuteException ex);
}
