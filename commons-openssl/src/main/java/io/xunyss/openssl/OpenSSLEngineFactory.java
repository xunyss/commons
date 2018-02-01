package io.xunyss.openssl;

/**
 * 
 * @author XUNYSS
 */
public class OpenSSLEngineFactory {
	
	/**
	 * 
	 */
	private static class SingletonHolder {
		// singleton object
		private static final OpenSSLEngineFactory instance = new OpenSSLEngineFactory();
	}
	
	/**
	 *
	 * @return
	 */
	public static OpenSSLEngineFactory getInstance() {
		return SingletonHolder.instance;
	}
	
	//----------------------------------------------------------------------------------------------
	
	/**
	 * Constructor.
	 */
	private OpenSSLEngineFactory() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public OpenSSLEngine createEngine() {
		// 아직 OpenSSLEngine 구현체가 OpenSSLBinaryEngine 밖에 없음
		return OpenSSLBinaryEngine.getInstance();
	}
}
