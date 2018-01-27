package io.xunyss.openssl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import io.xunyss.commons.io.NullOutputStream;
import io.xunyss.commons.io.WriterOutputStream;

/**
 * 
 * @author XUNYSS
 */
public class OpenSSL {
	
	/**
	 * OpenSSL command executor
	 */
	private OpenSSLExecutor openSSLExecutor;
	
	/**
	 * OpenSSL command output
	 */
	private OutputStream outputStream;
	
	
	/**
	 *
	 * @param outputStream
	 */
	public OpenSSL(OutputStream outputStream) {
		openSSLExecutor = OpenSSLExecutor.getInstance();
		setOutputStream(outputStream);
	}
	
	/**
	 *
	 * @param writer
	 */
	public OpenSSL(Writer writer) {
		this(new WriterOutputStream(writer));
	}
	
	/**
	 *
	 */
	public OpenSSL() {
		this(NullOutputStream.NULL_OUTPUT_STREAM);
	}
	
	/**
	 *
	 * @param outputStream
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	
	/**
	 *
	 * @param outputStream
	 * @param commands
	 * @throws IOException
	 */
	public void execute(OutputStream outputStream, String... commands) throws IOException {
		openSSLExecutor.execute(outputStream, commands);
	}
	
	/**
	 *
	 * @param commands
	 * @throws IOException
	 */
	public void execute(String... commands) throws IOException {
		execute(outputStream, commands);
	}
	
	
	public String version() {
		try {
			openSSLExecutor.execute(System.out, "version");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
