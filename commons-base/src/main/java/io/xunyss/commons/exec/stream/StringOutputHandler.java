package io.xunyss.commons.exec.stream;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import io.xunyss.commons.exec.PumpStreamHandler;

/**
 *
 * @author XUNYSS
 */
public class StringOutputHandler extends PumpStreamHandler {
	
	/**
	 * 
	 */
	public StringOutputHandler() {
		super(new ByteArrayOutputStream(), new ByteArrayOutputStream(), null);
		setCloseStreams(true);
	}
	
	
	@Override
	public void start() {
		getOut().reset();
		getErr().reset();
		
		super.start();
	}
	
	
	public String getOutputString() {
		return getOut().toString();
	}
	
	public String getErrorString() {
		return getErr().toString();
	}
	
	public String getOutputString(String charsetName) throws UnsupportedEncodingException {
		return getOut().toString(charsetName);
	}
	
	public String getErrorString(String charsetName) throws UnsupportedEncodingException {
		return getErr().toString(charsetName);
	}
	
	
	private ByteArrayOutputStream getOut() {
		return ((ByteArrayOutputStream) getOutputStream());
	}
	
	private ByteArrayOutputStream getErr() {
		return ((ByteArrayOutputStream) getErrorStream());
	}
}
