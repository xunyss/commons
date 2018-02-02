package io.xunyss.openssl;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.exec.stream.StringOutputHandler;

/**
 * 
 * @author XUNYSS
 */
public class OpenSSL {
	
	private BinaryInstaller binaryInstaller = BinaryInstaller.getInstance();
	
	private String binaryName;
	
	
	public OpenSSL() {
		binaryName = binaryInstaller.getBinaryName();
	}
	
	public void exec(String... commands) {
//		if (!initialized) {
//		throw new IOException("openSSL is not initialized");
//	}
		String binName = "/xdev/git/commons/commons-openssl/src/main/resources/io/xunyss/openssl/binary/win32/openssl.exe";
		try {
			StringOutputHandler stringOutputHandler = new StringOutputHandler();
			ProcessExecutor processExecutor = new ProcessExecutor(true);
			processExecutor.setStreamHandler(stringOutputHandler);
			
			processExecutor.execute(binaryName, commands);
		}
		catch (Exception ex) {
			ex.printStackTrace();
//			throw new IOException();
		}
	}
}
