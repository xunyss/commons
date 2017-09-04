package org.xunyss.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.xunyss.commons.io.IOUtils;

/**
 * 
 * @author XUNYSS
 */
public class FileUtils {
	
	public static final char FILE_SEPARATOR = '/';
	
	public FileUtils() {
		/* cannot create instance */
	}

	/**
	 * 
	 * @return
	 */
	public static String getTempDir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getSimpleFilename(String filePath) {
		int fileSepIndex = filePath.lastIndexOf(FILE_SEPARATOR);
		if (fileSepIndex != -1) {
			filePath = filePath.substring(fileSepIndex + 1);
		}
		if (FILE_SEPARATOR != File.separatorChar) {
			fileSepIndex = filePath.lastIndexOf(File.separatorChar);
			if (fileSepIndex != -1) {
				filePath = filePath.substring(fileSepIndex + 1);
			}
		}
		return filePath;
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static String getSimpleFilename(File file) {
		return getSimpleFilename(file.getPath());
	}

	/**
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void makeDirectory(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new IOException("directory '" + dir + "' is exist");
			}
		}
		else {
			if (!dir.mkdirs()) {
				throw new IOException("cannot create directory '" + dir + "'");
			}
		}
	}

	/**
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public static void deleteDirectory(File dir) throws IOException {
		if (!dir.isDirectory()) {
			throw new IOException("directory '" + dir + "' is not exist");
		}
		
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				deleteDirectory(file);
			}
			file.delete();
		}
		dir.delete();
	}

	/**
	 * 
	 * @param dir
	 */
	public static void deleteDirectoryQuietly(File dir) {
		try {
			deleteDirectory(dir);
		} catch (IOException ignored) {}
	}
	
	/**
	 * 
	 * @param srcFile
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copy(File srcFile, File dstFile) throws IOException {
		FileInputStream srcFileInputStream = new FileInputStream(srcFile);
		IOUtils.copy(srcFileInputStream, dstFile);
		IOUtils.closeQuietly(srcFileInputStream);
	}
	
	/**
	 * 
	 * @param srcDir
	 * @param dstDir
	 * @throws IOException
	 */
	public static void copyDirectory(File srcDir, File dstDir) throws IOException {
		// throws exception if fail to create directory
		makeDirectory(dstDir);
		
		File[] srcFiles = srcDir.listFiles();
		for (File srcFile : srcFiles) {
			File dstFile = new File(dstDir, srcFile.getName());
			if (srcFile.isDirectory()) {
				copyDirectory(srcFile, dstFile);
			}
			else {
				copy(srcFile, dstFile);
			}
		}
	}
}
