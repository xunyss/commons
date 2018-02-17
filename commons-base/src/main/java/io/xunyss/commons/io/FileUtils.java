package io.xunyss.commons.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.URL;

import io.xunyss.commons.lang.SystemUtils;

/**
 * File utilities.
 *
 * @author XUNYSS
 */
public final class FileUtils {
	
	/**
	 *
	 */
	public static final String FILE_SEPARATOR = File.separator;
	/**
	 *
	 */
	public static final char FILE_SEPARATOR_CHAR = File.separatorChar;
	/**
	 *
	 */
	public static final char UNIX_FILE_SEPARATOR_CHAR = '/';
	/**
	 *
	 */
	public static final char WINDOWS_FILE_SEPARATOR_CHAR = '\\';
	/**
	 *
	 */
	public static final char RESOURCE_PATH_SEPARATOR_CHAR = UNIX_FILE_SEPARATOR_CHAR;
	
	
	/**
	 * Constructor.
	 */
	private FileUtils() {
		// cannot create instance
	}
	
	/**
	 *
	 * @return
	 */
	public static File getTempDirectory() {
		return new File(SystemUtils.JAVA_IO_TMPDIR);
	}
	
	/**
	 *
	 * @param filePath
	 * @return
	 */
	public static String getSimpleFilename(String filePath) {
		int fileSeparatorIdx = filePath.lastIndexOf(UNIX_FILE_SEPARATOR_CHAR);
		if (fileSeparatorIdx != -1) {
			filePath = filePath.substring(fileSeparatorIdx + 1);
		}
		if (UNIX_FILE_SEPARATOR_CHAR != File.separatorChar) {
			fileSeparatorIdx = filePath.lastIndexOf(File.separatorChar);
			if (fileSeparatorIdx != -1) {
				filePath = filePath.substring(fileSeparatorIdx + 1);
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
	 * Copy contents from an InputStream to a File.
	 *
	 * @param srcInputStream input
	 * @param dstFile output
	 * @return the number of bytes copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(InputStream srcInputStream, File dstFile) throws IOException {
//		OutputStream dstOutputStream = new FileOutputStream(dstFile);
//		int count = copy(srcInputStream, dstOutputStream);
//		closeQuietly(dstOutputStream);
//		return count;
//		==>
		// 2018.01.20 XUNYSS
		// convert above statement to Java7 'try-with-resources' statement
		try (OutputStream dstOutputStream = new FileOutputStream(dstFile)) {
			return IOUtils.copy(srcInputStream, dstOutputStream);
		}
	}
	// removed bad 'API design' method
//	public static int copy(InputStream srcInputStream, String dstFilePath) throws IOException {
//		return copy(srcInputStream, new File(dstFilePath));
//	}
	
	/**
	 * Copy contents from a URL to a File.
	 *
	 * @param url input
	 * @param file output
	 * @return the number of bytes copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(URL url, File file) throws IOException {
		try (InputStream inputStream = url.openStream()) {
			return copy(inputStream, file);
		}
	}
	
	/**
	 * Copy contents from a File to a File.
	 *
	 * @param srcFile input
	 * @param dstFile output
	 * @return the number of bytes copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(File srcFile, File dstFile) throws IOException {
		try (InputStream srcInputStream = new FileInputStream(srcFile)) {
			return copy(srcInputStream, dstFile);
		}
	}
	
	/**
	 * Copy contents from a String to a File.
	 *
	 * @param srcString input
	 * @param dstFile output
	 * @return the number of characters copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(String srcString, File dstFile) throws IOException {
		try (StringReader srcStringReader = new StringReader(srcString);
				FileWriter dstFileWriter = new FileWriter(dstFile)) {
			return IOUtils.copy(srcStringReader, dstFileWriter);
		}
	}
	
	/**
	 *
	 * @param dir
	 * @throws IOException
	 */
	public static void makeDirectory(File dir) throws IOException {
		if (dir.exists()) {
			if (!dir.isDirectory()) {
				throw new IOException("File '" + dir + "' is already exist");
			}
		}
		else {
			if (!dir.mkdirs()) {
				throw new IOException("Cannot create directory '" + dir + "'");
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
			throw new IOException("Directory '" + dir + "' is not exist");
		}
		
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				// recursive
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
		}
		catch (IOException ex) {
			// ignore exception
		}
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
				// recursive
				copyDirectory(srcFile, dstFile);
			}
			else {
				copy(srcFile, dstFile);
			}
		}
	}
}
