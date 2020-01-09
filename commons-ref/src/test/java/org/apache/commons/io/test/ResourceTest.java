package org.apache.commons.io.test;

import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.ResourceUtils;

public class ResourceTest {

	@Test
	public void test() throws IOException {
		
		System.out.println("test2");
		
		URL url1 = IOUtils.resourceToURL("/empty");
		System.out.println(url1);
		
		URL url2 = IOUtils.resourceToURL("empty", ClassLoader.getSystemClassLoader());
		System.out.println(url2);
		
		URL url3 = IOUtils.resourceToURL("empty", ResourceTest.class.getClassLoader());
		System.out.println(url3);
		
		URL url4 = getClass().getResource("/empty");
		System.out.println(url4);
		
		System.out.println(ResourceUtils.getURL("/empty"));
	}
}
