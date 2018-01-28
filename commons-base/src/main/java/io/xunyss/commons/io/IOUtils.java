package io.xunyss.commons.io;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;

/**
 * I/O utilities.
 *
 * @author XUNYSS
 */
public final class IOUtils {
	
	/**
	 * Default buffer size.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
	
	/**
	 * Represents the end-of-file.
	 */
	private static final int EOF = -1;
	
	
	/**
	 * Constructor.
	 */
	private IOUtils() {
		// cannot create instance
	}
	
	/**
	 * Copy contents from an InputStream to an OutputStream.
	 *
	 * @param srcInputStream input
	 * @param dstOutputStream output
	 * @return the number of bytes copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(InputStream srcInputStream, OutputStream dstOutputStream) throws IOException {
		int count = 0;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		for (int readLen; (readLen = srcInputStream.read(buffer)) > EOF;) {
			dstOutputStream.write(buffer, 0, readLen);
			count += readLen;
		}
		dstOutputStream.flush();
		return count;
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
		
		// 2018.01.20 XUNYSS
		// convert above statement to Java7 'try-with-resources' statement
		try (OutputStream dstOutputStream = new FileOutputStream(dstFile)) {
			return copy(srcInputStream, dstOutputStream);
		}
	}
	// bad for 'API design'
//	public static int copy(InputStream srcInputStream, String dstFilePath) throws IOException {
//		return copy(srcInputStream, new File(dstFilePath));
//	}
	
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
			return IOUtils.copy(srcInputStream, dstFile);
		}
	}
	
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
	 * Copy contents from a Reader to a Writer.
	 *
	 * @param srcReader input
	 * @param dstWriter output
	 * @return the number of characters copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(Reader srcReader, Writer dstWriter) throws IOException {
		int count = 0;
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		for (int readLen; (readLen = srcReader.read(buffer)) > EOF;) {
			dstWriter.write(buffer, 0, readLen);
			count += readLen;
		}
		dstWriter.flush();
		return count;
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
			return copy(srcStringReader, dstFileWriter);
		}
	}
	
	/**
	 * Close a {@code Closeable} unconditionally.
	 * Recommend using try-with-resources statement.
	 *
	 * @param closeable closeable resource
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		}
		catch (IOException ex) {
			// ignore exception
		}
	}
}
