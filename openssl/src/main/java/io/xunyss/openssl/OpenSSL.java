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
	
	private BinaryExecOpenSSL binOpenSSL;
	private OutputStream outputStream;
	
	public OpenSSL(OutputStream outputStream) {
		binOpenSSL = BinaryExecOpenSSL.getInstance();
		setOutputStream(outputStream);
	}
	
	public OpenSSL(Writer writer) {
		this(new WriterOutputStream(writer));
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
	
	
	public String version() {
		return "";
	}
}
