package io.xunyss.commons.lang;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.xunyss.commons.io.IOUtils;

/**
 * 
 * @author XUNYSS
 */
public class JarClassLoader extends ClassLoader implements Closeable {
	
	/*
	 * 간단히 URLClassLoader 를 사용할 수도 있으나 .jar 파일에 접근하면 파일을 계속 물고 있는 문제 있음
	 * TODO: jar 파일 두개 이상 적용
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
	private JarFile jarFile = null;
	
	
	public JarClassLoader(File jarFile, ClassLoader parent) throws IOException {
		super(parent);
		setJarFile(jarFile);
	}
	
	public JarClassLoader(File jarFile) throws IOException {
		setJarFile(jarFile);
	}
	
	public JarClassLoader(String jarPath, ClassLoader parent) throws IOException {
		super(parent);
		setJarFile(jarPath);
	}
	
	public JarClassLoader(String jarPath) throws IOException {
		setJarFile(jarPath);
	}
	
	
	private void setJarFile(File jarFile) throws IOException {
		this.jarFile = new JarFile(jarFile);
	}
	
	private void setJarFile(String jarPath) throws IOException {
		this.jarFile = new JarFile(jarPath);
	}
	
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		InputStream classInputStream = null;
		
		try {
			JarEntry jarEntry = getClassJarEntry(name);
			
			if (jarEntry != null) {
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
	
	@Override
	public void close() {
		IOUtils.closeQuietly(jarFile);
	}
	
	
	private JarEntry getClassJarEntry(String className) {
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		JarEntry jarEntry;
		
		while (jarEntries.hasMoreElements()) {
			jarEntry = jarEntries.nextElement();
			
			if (jarEntry.getName().endsWith(ClassUtils.CLASS_FILE_SUFFIX) &&
					className.equals(getClassName(jarEntry))) {
				return jarEntry;
			}
		}
		
		return null;
	}
	
	private String getClassName(JarEntry jarEntry) {
		// TODO: Utility class method 로 구현
		return jarEntry.getName()
				.replace(ClassUtils.CLASS_FILE_SUFFIX, StringUtils.EMPTY)
				.replace(ClassUtils.PATH_SEPARATOR, ClassUtils.PACKAGE_SEPARATOR);
	}
}
