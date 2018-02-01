package io.xunyss.openssl;

/**
 * 
 * @author XUNYSS
 */
public interface OpenSSLEngine {
	
	/**
	 * 
	 * @param commands
	 * @return
	 */
	boolean execute(String... commands);
	
	/**
	 * 
	 * @return
	 */
	String version();
}
