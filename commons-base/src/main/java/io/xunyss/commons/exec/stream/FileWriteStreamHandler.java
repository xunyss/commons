package io.xunyss.commons.exec.stream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.xunyss.commons.exec.PumpStreamHandler;

/**
 * 
 * @author XUNYSS
 */
public class FileWriteStreamHandler extends PumpStreamHandler {
	
	public FileWriteStreamHandler(File file) throws IOException {
		super(new BufferedOutputStream(new FileOutputStream(file)));
	}
	
	public FileWriteStreamHandler(String filePath) throws IOException {
		this(new File(filePath));
	}
	
	
//	@Override
//	public void setAutoCloseStreams(boolean autoCloseStreams) {
//		// TODO: 에러처리 - 반드시 true 여야 함
//		if (!autoCloseStreams) {
//			throw new IllegalStateException("Cannot set 'false' autoCloseStreams property");
//		}
//	}
}
