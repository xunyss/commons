package io.xunyss.commons.io;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 
 * @author XUNYSS
 */
public final class ResourceUtils {
	
	/**
	 * 
	 */
	public static final String URL_PROTOCOL_FILE = "file";
	/**
	 * 
	 */
	public static final String URL_PROTOCOL_JAR = "jar";
	/**
	 * 
	 */
	public static final String URL_PROTOCOL_WAR = "war";
	/**
	 * 
	 */
	public static final String RESOURCE_ROOT = "/";
	/**
	 * 
	 */
	public static final String JAR_URL_SEPARATOR = "!/";
	/**
	 * 
	 */
	public static final String WAR_URL_SEPARATOR = "*/";
	
	
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
	 * @param name
	 * @param classLoader
	 * @return
	 */
	public static URL getResource(String name, ClassLoader classLoader) {
		return classLoader.getResource(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static URL getResource(String name) {
		return ResourceUtils.class.getResource(name);
	}
	
	/**
	 * 
	 * @param name
	 * @param classLoader
	 * @return
	 */
	public static InputStream getResourceAsStream(String name, ClassLoader classLoader) {
		return classLoader.getResourceAsStream(name);
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static InputStream getResourceAsStream(String name) {
		return ResourceUtils.class.getResourceAsStream(name);
	}
	
	/*
	 * getResource(String name)
	 * TODO:
	 * name 이 '/'로 시작하는지 그렇지 않을지를 확인하여 CLASSPATH '/'(root) 에서 부터 자동으로 찾아주는 메소드가 필요 없는지 확인
	 * ClassLoader.getResource() 는 name 이 '/'로 시작하면 안되기 때문
	 */
	
	/**
	 * 
	 * @param name
	 * @param classLoader
	 * @return
	 */
	public static File getResourceAsFile(String name, ClassLoader classLoader) {
		return getFile(getResource(name, classLoader));
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public static File getResourceAsFile(String name) {
		return getFile(getResource(name));
	}
	
	/**
	 * 
	 * @param resourceUrl
	 * @return
	 */
	public static File getFile(URL resourceUrl) {
		/*
		 * resourceUrl "jar:file:" 라면 error 발생
		 * TODO: 관련 처리 필요
		 */
		try {
			return new File(resourceUrl.toURI());
		}
		catch (URISyntaxException ex) {
			return new File(resourceUrl.getFile());
		}
	}
	
	/**
	 * 
	 * @param jarResourceUrl jar:file:/jar_file_path.jar!/resource/path
	 * @return file:/jar_file_path.jar
	 * @throws MalformedURLException
	 */
	public static URL getJarFileURL(URL jarResourceUrl) throws MalformedURLException {
		String resourcePath = jarResourceUrl.getPath();
		int separatorIdx = resourcePath.indexOf(JAR_URL_SEPARATOR);
		if (separatorIdx != -1) {
			return new URL(resourcePath.substring(0, separatorIdx));
		}
		else {
			return jarResourceUrl;
		}
	}
}
