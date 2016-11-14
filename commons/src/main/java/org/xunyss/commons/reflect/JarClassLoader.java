package org.xunyss.commons.reflect;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.xunyss.commons.util.IOUtils;

/**
 * 
 * @author XUNYSS
 */
public class JarClassLoader extends ClassLoader implements Closeable {

	/**
	 * 
	 * @param jarFile
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static Class<?> loadClass(File jarFile, String className) throws IOException, ClassNotFoundException {
		JarClassLoader jcl = null;
		try {
			jcl = new JarClassLoader(jarFile);
			return jcl.loadClass(className);
		}
		finally {
			jcl.close();
		}
	}
	
	/**
	 * 
	 */
	private JarFile jarFile = null;
	
	public JarClassLoader(File jarFile) throws IOException {
		setJarFile(jarFile);
	}

	public JarClassLoader(File jarFile, ClassLoader parent) throws IOException {
		super(parent);
		setJarFile(jarFile);
	}
	
	public JarClassLoader(String jarPath) throws IOException {
		setJarFile(jarPath);
	}
	
	public JarClassLoader(String jarPath, ClassLoader parent) throws IOException {
		super(parent);
		setJarFile(jarPath);
	}

	private void setJarFile(File jarFile) throws IOException {
		this.jarFile = new JarFile(jarFile);
	}
	
	private void setJarFile(String jarPath) throws IOException {
		this.jarFile = new JarFile(jarPath);
	}
	
	@Override
	public void close() {
		IOUtils.closeQuietly(jarFile);
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
		catch (Exception e) {
			throw new ClassNotFoundException(name, e);
		}
		finally {
			IOUtils.closeQuietly(classInputStream);
		}
		
		throw new ClassNotFoundException(name);
	}
	
	private JarEntry getClassJarEntry(String className) {
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		JarEntry jarEntry;
		
		while (jarEntries.hasMoreElements()) {
			jarEntry = jarEntries.nextElement();
			
			if (jarEntry.getName().endsWith(".class")
					&& className.equals(getClassName(jarEntry))) {
				return jarEntry;
			}
		}
		
		return null;
	}
	
	private String getClassName(JarEntry jarEntry) {
		return jarEntry.getName()
				.replace(".class", "")
				.replace("/", ".");
	}
}
