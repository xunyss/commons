package io.xunyss.commons.lang;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * @author XUNYSS
 */
public final class ResourceUtils {
	
	/**
	 * 
	 */
	public static final String URL_PROTOCOL_FILE      = "file";
	/**
	 * 
	 */
	public static final String URL_PROTOCOL_JAR       = "jar";
	/**
	 * 
	 */
	public static final String JAR_RESOURCE_ROOT      = "/";
	/**
	 * 
	 */
	public static final String JAR_RESOURCE_SEPARATOR = "!/";
	
	
	/**
	 * Constructor.
	 */
	private ResourceUtils() {
		// cannot create instance
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isFileURL(URL url) {
		return URL_PROTOCOL_FILE.equals(url.getProtocol());
	}
	
	/**
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isJarURL(URL url) {
		return URL_PROTOCOL_JAR.equals(url.getProtocol());
	}

	/**
	 * 
	 * @param jarResourceUrl file:/jar_file_path.jar!/resource/path
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getJarFileURL(URL jarResourceUrl) throws MalformedURLException {
		String resourcePath = jarResourceUrl.getPath();
		int separatorIdx = resourcePath.indexOf(JAR_RESOURCE_SEPARATOR);
		if (separatorIdx != -1) {
			return new URL(resourcePath.substring(0, separatorIdx));
		}
		else {
			return jarResourceUrl;
		}
	}
}
