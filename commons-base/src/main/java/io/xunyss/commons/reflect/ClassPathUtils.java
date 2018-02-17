package io.xunyss.commons.reflect;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 
 * @author XUNYSS
 */
public class ClassPathUtils {
	
	private static final URLClassLoader systemClassLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
	
	
	public static void add(URL url) {
		try {
			Method method = URLClassLoader.class
					.getDeclaredMethod("addURL", URL.class);
			
			method.setAccessible(true);
			method.invoke(systemClassLoader, url);
		}
		catch (Exception ex) {
			// ignore exception
		}
	}
	
	public static void add(File file) {
		try {
			add(file.toURI().toURL());
		}
		catch (Exception ex) {
			// ignore exception
		}
	}
	
	public static void addDirectory(File directory) {
		if (directory.isDirectory()) {
			File[] jars = directory.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					return pathname.isFile() && pathname.getName().endsWith(".jar");
				}
			});
			
			for (File jar : jars) {
				add(jar);
			}
		}
	}
}
