package org.xunyss.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import io.xunyss.commons.io.FileUtils;
import io.xunyss.commons.io.IOUtils;
import io.xunyss.commons.lang.StringUtils;

/**
 *
 * @author XUNYSS
 */
public class ArchiveUtils {

	private ArchiveUtils() {
		/* cannot create instance */
	}

	/**
	 * 
	 * @param zipFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unzip(File zipFile, File dstDir) throws IOException {
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry;

		try {
			zipInputStream = new ZipInputStream(new FileInputStream(zipFile));

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String filename = zipEntry.getName();
				File entryFile = new File(dstDir, filename);

				if (zipEntry.isDirectory()) {
					entryFile.mkdirs();
				}
				else {
					entryFile.getParentFile().mkdirs();
					IOUtils.copy(zipInputStream, entryFile);
				}
			}
		}
		finally {
			IOUtils.closeQuietly(zipInputStream);
		}
	}

	/**
	 * 
	 * @param zipFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unzip(File zipFile, String dstDir) throws IOException {
		unzip(zipFile, new File(dstDir));
	}

	/**
	 * 
	 * @param zipFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unzip(String zipFile, File dstDir) throws IOException {
		unzip(new File(zipFile), dstDir);
	}

	/**
	 * 
	 * @param zipFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unzip(String zipFile, String dstDir) throws IOException {
		unzip(new File(zipFile), new File(dstDir));
	}

	/**
	 * 
	 * @param jarFile
	 * @param resourcePath
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(JarFile jarFile, String resourcePath, File dstDir) throws IOException {
		if (!resourcePath.startsWith(ResourceUtils.JAR_RESOURCE_ROOT)) {
			throw new IOException("resource path must be absolute: " + resourcePath);
		}
		
		Enumeration<JarEntry> entries = jarFile.entries();
		JarEntry jarEntry;
		String jarResource;
		
		while (entries.hasMoreElements()) {
			jarEntry = entries.nextElement();
			jarResource = ResourceUtils.JAR_RESOURCE_ROOT + jarEntry.getName();
			
			if (jarResource.startsWith(resourcePath)) {
				String relativePath = StringUtils.removeStart(jarResource, resourcePath);
				File dstFile = relativePath.isEmpty() ?
						new File(dstDir, FileUtils.getSimpleFilename(jarResource)) :	// resourcePath is file
						new File(dstDir, relativePath);									// resourcePath is directory
				
				if (jarEntry.isDirectory()) {
					dstFile.mkdirs();
				}
				else {
					copy(jarFile, jarEntry, dstFile);
				}
			}
		}
	}

	/**
	 * 
	 * @param jarFileUrl
	 * @param resourcePath
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(URL jarFileUrl, String resourcePath, File dstDir) throws IOException {
		JarFile jarFile = new JarFile(jarFileUrl.getPath());
		unjar(jarFile, resourcePath, dstDir);
		IOUtils.closeQuietly(jarFile);
	}
	
	/**
	 * 
	 * @param jarFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(JarFile jarFile, File dstDir) throws IOException {
		unjar(jarFile, ResourceUtils.JAR_RESOURCE_ROOT, dstDir);
	}

	/**
	 * 
	 * @param jarFileUrl
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(URL jarFileUrl, File dstDir) throws IOException {
		unjar(jarFileUrl, ResourceUtils.JAR_RESOURCE_ROOT, dstDir);
	}
	
	/**
	 * 
	 * @param srcJarFile
	 * @param srcJarEntry
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copy(JarFile srcJarFile, JarEntry srcJarEntry, File dstFile) throws IOException {
		if (srcJarEntry.isDirectory()) {
			throw new IOException("cannot copy jar resource directory");
		}
		
		InputStream inputStream = srcJarFile.getInputStream(srcJarEntry);
		IOUtils.copy(inputStream, dstFile);
		IOUtils.closeQuietly(inputStream);
	}
}
