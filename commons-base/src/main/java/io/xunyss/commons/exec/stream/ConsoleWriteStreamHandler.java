package io.xunyss.commons.exec.stream;

import io.xunyss.commons.exec.StreamHandler;

/**
 * 
 * @author XUNYSS
 */
public class ConsoleWriteStreamHandler extends StreamHandler {

	public ConsoleWriteStreamHandler() {
//		super(System.out, System.err, System.in);
		super(System.out, System.err);
	}
}
