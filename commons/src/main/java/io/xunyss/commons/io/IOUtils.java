package io.xunyss.commons.io;

import java.io.Closeable;
import java.io.File;
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
 *
 * @author XUNYSS
 */
public final class IOUtils {
	
	/**
	 * default buffer size
	 */
	private static final int DEFAULT_BUFFER_SIZE = 8192;
	
	/**
	 * end of file
	 */
	private static final int EOF = -1;
	
	
	/**
	 * constructor
	 */
	private IOUtils() {
		/* cannot create instance */
	}
	
	/**
	 *
	 * @param srcInputStream
	 * @param dstOutputStream
	 * @throws IOException
	 */
	public static void copy(InputStream srcInputStream, OutputStream dstOutputStream) throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		for (int readLen; (readLen = srcInputStream.read(buffer)) > EOF;) {
			dstOutputStream.write(buffer, 0, readLen);
		}
		dstOutputStream.flush();
	}
	
	/**
	 *
	 * @param srcInputStream
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copy(InputStream srcInputStream, File dstFile) throws IOException {
		OutputStream dstOutputStream = new FileOutputStream(dstFile);
		copy(srcInputStream, dstOutputStream);
		closeQuietly(dstOutputStream);
	}
	
	/**
	 *
	 * @param srcInputStream
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copy(InputStream srcInputStream, String dstFile) throws IOException {
		copy(srcInputStream, new File(dstFile));
	}
	
	/**
	 *
	 * @param srcReader
	 * @param dstWriter
	 * @throws IOException
	 */
	public static void copy(Reader srcReader, Writer dstWriter) throws IOException {
		char[] buffer = new char[DEFAULT_BUFFER_SIZE];
		for (int readLen; (readLen = srcReader.read(buffer)) > EOF;) {
			dstWriter.write(buffer, 0, readLen);
		}
		dstWriter.flush();
	}
	
	/**
	 *
	 * @param srcStr
	 * @param dstFile
	 * @throws IOException
	 */
	public static void copy(String srcStr, File dstFile) throws IOException {
		StringReader srcStrReader = new StringReader(srcStr);
		FileWriter dstFileWriter = new FileWriter(dstFile);
		copy(srcStrReader, dstFileWriter);
		
		closeQuietly(srcStrReader);
		closeQuietly(dstFileWriter);
	}
	
	/**
	 *
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void copy(URL url, File file) throws IOException {
		InputStream inputStream = url.openStream();
		copy(inputStream, file);
		
		closeQuietly(inputStream);
	}
	
	/**
	 *
	 * @param url
	 * @param file
	 * @throws IOException
	 */
	public static void copy(URL url, String file) throws IOException {
		copy(url, new File(file));
	}
	
	/**
	 *
	 * @param closeable
	 */
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		}
		catch (IOException ignored) {
			/* ignore exception */
		}
	}
}
