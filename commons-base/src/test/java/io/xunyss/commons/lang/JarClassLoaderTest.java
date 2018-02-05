package io.xunyss.commons.lang;

import java.io.File;

import org.junit.Test;

import io.xunyss.commons.io.ResourceUtils;

/**
 * Unit tests for the JarClassLoader class.
 *
 * @author XUNYSS
 */
public class JarClassLoaderTest {
	
	@Test
	public void sys() throws Exception {
		ClassLoader cl = getClass().getClassLoader();
		ClassLoader.getSystemClassLoader();
		Thread.currentThread().getContextClassLoader();
		
		cl.loadClass("..");
		Class.forName("");
	}
	
	@Test
	public void cl() {
		System.out.println(getClass().getClassLoader());
		System.out.println(ClassLoader.getSystemClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader());
		System.out.println(Thread.currentThread().getContextClassLoader().getParent());
		System.out.println(Thread.currentThread().getContextClassLoader().getParent().getParent());
	}
	
	@Test
	public void load1() throws Exception {
		File jarFile = ResourceUtils.getResourceFile("/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar");
		try (JarClassLoader jarClassLoader = new JarClassLoader(jarFile)) {
			Class<?> clazz = jarClassLoader.loadClass("javax.servlet.Servlet");
			System.out.println(clazz.getName());
		}
	}
	
	@Test
	public void load2() throws Exception {
		JarClassLoader jcl = new JarClassLoader("class_dir");
	}
	
	// array constructor
	// URL
	// FileInputStream
	// add method.. -> add("bb.jar");
	// 
}
