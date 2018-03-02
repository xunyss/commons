package io.xunyss.commons.lang;

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
import io.xunyss.commons.io.ResourceUtils;

/**
 *
 * @author XUNYSS
 */
public final class ZipUtils {
	
	/**
	 * Constructor.
	 */
	private ZipUtils() {
		// cannot create instance
	}
	
	/**
	 * 
	 * @param srcInputStream
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unzip(InputStream srcInputStream, File dstDir) throws IOException {
		ZipInputStream zipInputStream = null;
		ZipEntry zipEntry;

		try {
			zipInputStream = new ZipInputStream(srcInputStream);

			while ((zipEntry = zipInputStream.getNextEntry()) != null) {
				String filename = zipEntry.getName();
				File entryFile = new File(dstDir, filename);

				if (zipEntry.isDirectory()) {
					FileUtils.makeDirectory(entryFile);
				}
				else {
					FileUtils.makeDirectory(entryFile.getParentFile());
					FileUtils.copy(zipInputStream, entryFile);
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
	public static void unzip(File zipFile, File dstDir) throws IOException {
		unzip(new FileInputStream(zipFile), dstDir);
	}

	/**
	 * 
	 * @param jarFile
	 * @param resourcePath
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(JarFile jarFile, String resourcePath, File dstDir) throws IOException {
		if (!resourcePath.startsWith(ResourceUtils.RESOURCE_ROOT)) {
			throw new IOException("Resource path must be absolute");
		}
		
		Enumeration<JarEntry> entries = jarFile.entries();
		JarEntry jarEntry;
		String jarResource;
		
		while (entries.hasMoreElements()) {
			jarEntry = entries.nextElement();
			jarResource = ResourceUtils.RESOURCE_ROOT + jarEntry.getName();
			
			if (jarResource.startsWith(resourcePath)) {
				String relativePath = StringUtils.removeStart(jarResource, resourcePath);
				File dstFile = relativePath.isEmpty() ?
						new File(dstDir, FileUtils.getSimpleFilename(jarResource)) :	// resourcePath is file
						new File(dstDir, relativePath);									// resourcePath is directory
				
				if (jarEntry.isDirectory()) {
					FileUtils.makeDirectory(dstFile);
				}
				else {
					copyJarEntry(jarFile, jarEntry, dstFile);
				}
			}
		}
	}
	
	/**
	 * 
	 * @param jarFile
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(JarFile jarFile, File dstDir) throws IOException {
		unjar(jarFile, ResourceUtils.RESOURCE_ROOT, dstDir);
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
	 * @param jarFileUrl
	 * @param dstDir
	 * @throws IOException
	 */
	public static void unjar(URL jarFileUrl, File dstDir) throws IOException {
		unjar(jarFileUrl, ResourceUtils.RESOURCE_ROOT, dstDir);
	}
	
	/**
	 * 
	 * @param srcJarFile
	 * @param srcJarEntry
	 * @param dstFile
	 * @throws IOException
	 */
	private static void copyJarEntry(JarFile srcJarFile, JarEntry srcJarEntry, File dstFile) throws IOException {
		if (srcJarEntry.isDirectory()) {
			throw new IOException("Cannot copy jar resource directory");
		}
		
		try (InputStream inputStream = srcJarFile.getInputStream(srcJarEntry)) {
			FileUtils.copy(inputStream, dstFile);			
		}
	}
}
