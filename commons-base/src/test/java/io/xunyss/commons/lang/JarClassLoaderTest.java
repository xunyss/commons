package io.xunyss.commons.lang;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import io.xunyss.commons.io.ResourceUtils;

/**
 * Unit tests for the JarClassLoader class.
 *
 * @author XUNYSS
 */
public class JarClassLoaderTest {
	
	@Test(expected = ExceptionInInitializerError.class)
	public void classLoadByForName() throws ClassNotFoundException {
		String className = "io.xunyss.commons.lang.ClassLoadingTestClass";
		Class.forName(className);
	}
	
	@Test
	public void classLoadByLoadClass() throws ClassNotFoundException {
		String className = "io.xunyss.commons.lang.ClassLoadingTestClass";
		ClassLoader classLoader = getClass().getClassLoader();
		classLoader.loadClass(className);
	}
	
	@Test
	public void classLoaders() {
		Assert.assertEquals("sun.misc.Launcher$AppClassLoader", getClass().getClassLoader()  .getClass().getName());
		Assert.assertEquals("sun.misc.Launcher$AppClassLoader", ClassLoader.getSystemClassLoader()  .getClass().getName());
		Assert.assertEquals("sun.misc.Launcher$AppClassLoader", Thread.currentThread().getContextClassLoader()  .getClass().getName());
		Assert.assertEquals("sun.misc.Launcher$ExtClassLoader", Thread.currentThread().getContextClassLoader().getParent()  .getClass().getName());
		Assert.assertNull(Thread.currentThread().getContextClassLoader().getParent().getParent());
		
		Assert.assertEquals(getClass().getClassLoader(), ClassLoader.getSystemClassLoader());
		Assert.assertEquals(getClass().getClassLoader(), Thread.currentThread().getContextClassLoader());
		Assert.assertNotEquals(getClass().getClassLoader(), getClass().getClassLoader().getParent());
	}
	
	@Test
	public void loadClass() throws IOException, ClassNotFoundException {
		File file = ResourceUtils.getResourceAsFile("/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar");
		
		Class<?> clazz;
		try (JarClassLoader jarClassLoader = new JarClassLoader(file)) {
			clazz = jarClassLoader.loadClass("javax.servlet.Servlet");
		}
		
		Assert.assertEquals("javax.servlet.Servlet", clazz.getName());
	}
	
	@Test
	public void loadClassMultiFiles() throws IOException, ClassNotFoundException {
		File file1 = ResourceUtils.getResourceAsFile("/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar");
		File file2 = ResourceUtils.getResourceAsFile("/io/xunyss/commons/lang/javax.servlet.jsp-api-2.3.1.jar");
		
		Class<?> clazz1, clazz2;
		try (JarClassLoader jarClassLoader = new JarClassLoader(ArrayUtils.toArray(file1, file2))) {
			clazz1 = jarClassLoader.loadClass("javax.servlet.Servlet");
			clazz2 = jarClassLoader.loadClass("javax.servlet.jsp.JspContext");
		}
		
		Assert.assertEquals("javax.servlet.Servlet", clazz1.getName());
		Assert.assertEquals("javax.servlet.jsp.JspContext", clazz2.getName());
	}
	
	@Test
	public void addFile() throws IOException, ClassNotFoundException {
		Class<?> clazz1, clazz2;
		try (JarClassLoader jarClassLoader = new JarClassLoader()) {
			jarClassLoader.addFile(ResourceUtils.getResourceAsFile("/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar"));
			jarClassLoader.addFile(ResourceUtils.getResourceAsFile("/io/xunyss/commons/lang/javax.servlet.jsp-api-2.3.1.jar"));
			
			clazz1 = jarClassLoader.loadClass("javax.servlet.Servlet");
			clazz2 = jarClassLoader.loadClass("javax.servlet.jsp.JspContext");
		}
		
		Assert.assertEquals("javax.servlet.Servlet", clazz1.getName());
		Assert.assertEquals("javax.servlet.jsp.JspContext", clazz2.getName());
	}
	
	@Test
	public void addURL() throws IOException, ClassNotFoundException {
		Class<?> clazz1, clazz2;
		try (JarClassLoader jarClassLoader = new JarClassLoader()) {
			jarClassLoader.addURL(ResourceUtils.getResource("/io/xunyss/commons/lang/javax.servlet-api-3.1.0.jar"));
			jarClassLoader.addURL(ResourceUtils.getResource("/io/xunyss/commons/lang/javax.servlet.jsp-api-2.3.1.jar"));
			
			clazz1 = jarClassLoader.loadClass("javax.servlet.Servlet");
			clazz2 = jarClassLoader.loadClass("javax.servlet.jsp.JspContext");
		}
		
		Assert.assertEquals("javax.servlet.Servlet", clazz1.getName());
		Assert.assertEquals("javax.servlet.jsp.JspContext", clazz2.getName());
	}
}
