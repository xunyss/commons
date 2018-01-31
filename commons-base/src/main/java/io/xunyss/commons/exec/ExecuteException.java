package io.xunyss.commons.exec;

import java.io.IOException;

/**
 *
 * @author XUNYSS
 */
@SuppressWarnings("serial")
public class ExecuteException extends IOException {
	
	public ExecuteException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ExecuteException(String message) {
		super(message);
	}
	
	public ExecuteException(Throwable cause) {
		super(cause);
	}
}
