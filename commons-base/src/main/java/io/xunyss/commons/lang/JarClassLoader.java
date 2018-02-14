package io.xunyss.commons.lang;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.xunyss.commons.io.IOUtils;
import io.xunyss.commons.io.ResourceUtils;

/**
 * 
 * @author XUNYSS
 */
public class JarClassLoader extends ClassLoader implements Closeable {
	
	/*
	 * 간단히 URLClassLoader 를 사용할 수도 있으나 .jar 파일에 접근하면 파일을 계속 물고 있는 문제 있음
	 */
	
	// 2018.02.05 XUNYSS
	// remove static method
//	/**
//	 * 
//	 * @param jarFile
//	 * @param className
//	 * @return
//	 * @throws Exception
//	 */
//	public static Class<?> loadClass(File jarFile, String className) throws IOException, ClassNotFoundException {
//		JarClassLoader jcl = null;
//		try {
//			jcl = new JarClassLoader(jarFile);
//			return jcl.loadClass(className);
//		}
//		finally {
//			jcl.close();
//		}
//	}
	
	/**
	 * 
	 */
	private JarFile[] jarFiles = null;
	
	
	/**
	 * 
	 * @param files
	 * @param parent
	 * @throws IOException
	 */
	public JarClassLoader(File[] files, ClassLoader parent) throws IOException {
		super(parent);
		addFile(files);
	}
	
	/**
	 * 
	 * @param files
	 * @throws IOException
	 */
	public JarClassLoader(File[] files) throws IOException {
		addFile(files);
	}
	
	/**
	 * 
	 * @param file
	 * @param parent
	 * @throws IOException
	 */
	public JarClassLoader(File file, ClassLoader parent) throws IOException {
		this(ArrayUtils.toArray(file), parent);
	}
	
	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public JarClassLoader(File file) throws IOException {
		this(ArrayUtils.toArray(file));
	}
	
	/**
	 * 
	 * @param urls
	 * @param parent
	 * @throws IOException
	 */
	public JarClassLoader(URL[] urls, ClassLoader parent) throws IOException {
		super(parent);
		addURL(urls);
	}
	
	/**
	 * 
	 * @param urls
	 * @throws IOException
	 */
	public JarClassLoader(URL[] urls) throws IOException {
		addURL(urls);
	}
	
	/**
	 * 
	 * @param url
	 * @param parent
	 * @throws IOException
	 */
	public JarClassLoader(URL url, ClassLoader parent) throws IOException {
		this(ArrayUtils.toArray(url), parent);
	}
	
	/**
	 * 
	 * @param url
	 * @throws IOException
	 */
	public JarClassLoader(URL url) throws IOException {
		this(ArrayUtils.toArray(url));
	}
	
	/**
	 * 
	 * @param parent
	 */
	public JarClassLoader(ClassLoader parent) {
		super(parent);
	}
	
	/**
	 * 
	 */
	public JarClassLoader() {
		
	}
	
	
	/**
	 * 
	 * @param files
	 * @throws IOException
	 */
	public void addFile(File... files) throws IOException {
		if (jarFiles != null && jarFiles.length > 0) {
			jarFiles = ArrayUtils.add(jarFiles, toJarFiles(files));
		}
		else {
			jarFiles = toJarFiles(files);
		}
	}
	
	/**
	 * 
	 * @param urls
	 * @throws IOException
	 */
	public void addURL(URL... urls) throws IOException {
		if (jarFiles != null && jarFiles.length > 0) {
			jarFiles = ArrayUtils.add(jarFiles, toJarFiles(urls));
		}
		else {
			jarFiles = toJarFiles(urls);
		}
	}
	
	/**
	 * 
	 * @param files
	 * @return
	 * @throws IOException
	 */
	private JarFile[] toJarFiles(File[] files) throws IOException {
		JarFile[] jarFiles = new JarFile[files.length];
		for (int idx = 0; idx < files.length; idx++) {
			jarFiles[idx] = new JarFile(files[idx]);
		}
		return jarFiles;
	}
	
	/**
	 * 
	 * @param urls
	 * @return
	 * @throws IOException
	 */
	public JarFile[] toJarFiles(URL[] urls) throws IOException {
		JarFile[] jarFiles = new JarFile[urls.length];
		for (int idx = 0; idx < urls.length; idx++) {
			jarFiles[idx] = new JarFile(ResourceUtils.getFile(urls[idx]));
		}
		return jarFiles;
	}
	
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		InputStream classInputStream = null;
		
		try {
			JarFileEntry jarFileEntry = getClassJarFileEntry(name);
			
			if (jarFileEntry != null) {
				JarFile jarFile = jarFileEntry.getJarFile();
				JarEntry jarEntry = jarFileEntry.getJarEntry();
				
				byte[] classData = new byte[(int) jarEntry.getSize()];
				classInputStream = jarFile.getInputStream(jarEntry);
				classInputStream.read(classData);
				
				return defineClass(name, classData, 0, classData.length);
			}
		}
		catch (IOException ex) {
			throw new ClassNotFoundException(name, ex);
		}
		finally {
			IOUtils.closeQuietly(classInputStream);
		}
		
		throw new ClassNotFoundException(name);
	}
	
	private JarFileEntry getClassJarFileEntry(String className) {
		for (JarFile jarFile : jarFiles) {
			
			Enumeration<JarEntry> jarEntries = jarFile.entries();
			JarEntry jarEntry;
			
			while (jarEntries.hasMoreElements()) {
				jarEntry = jarEntries.nextElement();
				
				if (jarEntry.getName().endsWith(ClassUtils.CLASS_FILE_SUFFIX) &&
						className.equals(getClassName(jarEntry))) {
					
					return new JarFileEntry(jarFile, jarEntry);
				}
			}
		}
		// class not found
		return null;
	}
	
	private String getClassName(JarEntry jarEntry) {
		// TODO: Utility class method 로 구현
		return jarEntry.getName()
				.replace(ClassUtils.CLASS_FILE_SUFFIX, StringUtils.EMPTY)
				.replace(ClassUtils.PATH_SEPARATOR, ClassUtils.PACKAGE_SEPARATOR);
	}
	
	@Override
	public void close() {
		for (JarFile jarFile : jarFiles) {
			IOUtils.closeQuietly(jarFile);
		}
	}
	
	
	/**
	 * 
	 * @author XUNYSS
	 */
	private class JarFileEntry {
		
		JarFile jarFile;
		JarEntry jarEntry;
		
		JarFileEntry(JarFile jarFile, JarEntry jarEntry) {
			this.jarFile = jarFile;
			this.jarEntry = jarEntry;
		}
		
		JarFile getJarFile() {
			return jarFile;
		}
		
		JarEntry getJarEntry() {
			return jarEntry;
		}
	}
}
