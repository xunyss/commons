package io.xunyss.openssl;

/**
 * Proxy.
 * 
 * @author XUNYSS
 */
public class OpenSSL {
	
	/**
	 * 
	 */
	private static OpenSSLEngineFactory engineFactory = OpenSSLEngineFactory.getInstance();
	
	
	/**
	 * OpenSSL command executor.
	 */
	private OpenSSLEngine openSSLEngine;
	
	
	public OpenSSL() {
		// set Engine
		openSSLEngine = engineFactory.createEngine();
	}
	
//	private static OpenSSL instance = new OpenSSL();
//	public static OpenSSL getInstance() {
//		return instance;
//	}
	
	
	public void exec(String... commands) {
		openSSLEngine.execute(commands);
	}
}
