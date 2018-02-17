package io.xunyss.commons.lang;

/**
 * System utilities.
 *
 * @author XUNYSS
 */
public final class SystemUtils {
	
	private static final String OS_NAME_WINDOWS_PREFIX = "Windows";
	
	public static final String OS_NAME = getSystemProperty("os.name");
	public static final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");
	
	public static final String USER_DIR = getSystemProperty("user.dir");
	
	public static final boolean IS_OS_WINDOWS = OS_NAME.startsWith(OS_NAME_WINDOWS_PREFIX);
	
	
	/**
	 * Constructor.
	 */
	private SystemUtils() {
		// cannot create instance
	}
	
	/**
	 * Get a system property.
	 *
	 * @param key system property key
	 * @return system property value
	 */
	public static String getSystemProperty(final String key) {
		return System.getProperty(key);
	}
}
