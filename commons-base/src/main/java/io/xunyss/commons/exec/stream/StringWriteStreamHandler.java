package io.xunyss.commons.exec.stream;

import io.xunyss.commons.exec.StreamHandler;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * 
 * @author XUNYSS
 */
public class StringWriteStreamHandler extends StreamHandler {
	
	private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
	private ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
	
	public StringWriteStreamHandler() {
		setOutputStream(outputBuffer);
		setErrorStream(errorBuffer);
	}
	
	public String getOutputString(String charsetName) throws UnsupportedEncodingException {
		return outputBuffer.toString(charsetName);
	}
	
	public String getErrorString(String charsetName) throws UnsupportedEncodingException {
		return errorBuffer.toString(charsetName);
	}
}
