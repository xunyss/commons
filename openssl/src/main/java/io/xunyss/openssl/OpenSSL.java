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
	
	private OpenSSLExecutor openSSLExecutor;
	private OutputStream outputStream;
	
	
	public OpenSSL(OutputStream outputStream) {
		openSSLExecutor = OpenSSLExecutor.getInstance();
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
	
	
	public void exec(OutputStream outputStream, String... args) throws IOException {
		openSSLExecutor.exec(outputStream, args);
	}
	
	public void exec(String... args) throws IOException {
		exec(outputStream, args);
	}
	
	
	public String version() {
		try {
			openSSLExecutor.exec(System.out, "version");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		new OpenSSL().version();
	}
}
