package io.xunyss.commons.exec.stream;

import io.xunyss.commons.exec.PumpStreamHandler;

/**
 * 
 * @author XUNYSS
 */
public class ConsoleWriteStreamHandler extends PumpStreamHandler {

	public ConsoleWriteStreamHandler() {
		super(System.out, System.err);
		setAutoCloseStreams(false);
	}
}
