package io.xunyss.commons.exec;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * @author XUNYSS
 */
public class FileStreamHandler extends StreamHandler {
	
	public FileStreamHandler(File file) throws IOException {
		super(new BufferedOutputStream(new FileOutputStream(file)));
	}
	
	public FileStreamHandler(String filePath) throws IOException {
		this(new File(filePath));
	}
	
	
	@Override
	public void setAutoCloseStreams(boolean autoCloseStreams) {
		throw new ExecuteException();
	}
}
