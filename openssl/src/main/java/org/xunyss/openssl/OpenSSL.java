package org.xunyss.openssl;

import java.io.IOException;
import java.io.OutputStream;

import org.xunyss.commons.io.NullOutputStream;

/**
 * 
 * @author XUNYSS
 */
public class OpenSSL {
	
	private BinaryExecOpenSSL binOpenSSL;
	private OutputStream outputStream;
	
	public OpenSSL(OutputStream outputStream) {
		binOpenSSL = BinaryExecOpenSSL.getInstance();
		setOutputStream(outputStream);
	}
	
	public OpenSSL() {
		this(NullOutputStream.NULL_OUTPUT_STREAM);
	}
	
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public void exec(String... args) throws IOException {
		binOpenSSL.exec(outputStream, args);
	}
}
