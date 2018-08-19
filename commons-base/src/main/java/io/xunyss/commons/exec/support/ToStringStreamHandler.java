package io.xunyss.commons.exec.support;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import io.xunyss.commons.exec.PumpStreamHandler;

/**
 *
 * @author XUNYSS
 */
public class ToStringStreamHandler extends PumpStreamHandler {
	
	/**
	 * 
	 */
	public ToStringStreamHandler() {
		super(new ByteArrayOutputStream(), new ByteArrayOutputStream(), null);
		setCloseStreams(true);
	}
	
	
	/**
	 *
	 */
	@Override
	public void start() {
		getOut().reset();
		getErr().reset();
		
		super.start();
	}
	
	
	/**
	 *
	 * @return
	 */
	public String getOutputString() {
		return getOut().toString();
	}
	
	/**
	 *
	 * @return
	 */
	public String getErrorString() {
		return getErr().toString();
	}
	
	/**
	 *
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getOutputString(String charsetName) throws UnsupportedEncodingException {
		return getOut().toString(charsetName);
	}
	
	/**
	 *
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String getErrorString(String charsetName) throws UnsupportedEncodingException {
		return getErr().toString(charsetName);
	}
	
	
	/**
	 *
	 * @return
	 */
	private ByteArrayOutputStream getOut() {
		return (ByteArrayOutputStream) getOutputStream();
	}
	
	/**
	 *
	 * @return
	 */
	private ByteArrayOutputStream getErr() {
		return (ByteArrayOutputStream) getErrorStream();
	}
}
