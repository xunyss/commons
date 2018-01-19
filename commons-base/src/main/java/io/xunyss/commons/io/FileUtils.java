package io.xunyss.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import io.xunyss.commons.lang.SystemUtils;

/**
 *
 * @author XUNYSS
 */
public final class FileUtils {
	
	public static final String FILE_SEPARATOR = File.separator;
	public static final char FILE_SEPARATOR_CHAR = File.separatorChar;
	public static final char UNIX_FILE_SEPARATOR_CHAR = '/';
	public static final char WINDOWS_FILE_SEPARATOR_CHAR = '\\';
	public static final char RESOURCE_PATH_SEPARATOR_CHAR = UNIX_FILE_SEPARATOR_CHAR;
	
	
	/**
	 * constructor
	 */
	private FileUtils() {
		/* cannot create instance */
	}
	
	/**
	 *
	 * @return
	 */
	public static String getTempDirectoryPath() {
		return SystemUtils.JAVA_IO_TMPDIR;
	}
	
	/**
	 *
	 * @param filePath
	 * @return
	 */
	public static String getSimpleFilename(String filePath) {
		int fileSepIndex = filePath.lastIndexOf(UNIX_FILE_SEPARATOR_CHAR);
		if (fileSepIndex != -1) {
			filePath = filePath.substring(fileSepIndex + 1);
		}
		if (UNIX_FILE_SEPARATOR_CHAR != File.separatorChar) {
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
