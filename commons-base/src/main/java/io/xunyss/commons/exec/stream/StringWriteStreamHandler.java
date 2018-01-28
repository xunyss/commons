package io.xunyss.commons.exec.stream;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import io.xunyss.commons.exec.PumpStreamHandler;

/**
 * 
 * @author XUNYSS
 */
public class StringWriteStreamHandler extends PumpStreamHandler {
	
	private ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
	private ByteArrayOutputStream errorBuffer = new ByteArrayOutputStream();
	
	public StringWriteStreamHandler() {
		//super(outputBuffer, errorBuffer);
		super(null, null);
	}
	
	public String getOutputString(String charsetName) throws UnsupportedEncodingException {
		return outputBuffer.toString(charsetName);
	}
	
	public String getErrorString(String charsetName) throws UnsupportedEncodingException {
		return errorBuffer.toString(charsetName);
	}
}
