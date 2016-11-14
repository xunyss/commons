package org.xunyss.commons.util;

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
public class IOUtils {

	private static final int BUFFER_SIZE = 8192;

	private static final int EOF = -1;

	private IOUtils() {
		/* cannot create instance */
	}
	
	public static void closeQuietly(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (IOException ignored) {}
	}
	
	public static void copy(InputStream srcInputStream, OutputStream dstOutputStream) throws IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		for (int readLen; (readLen = srcInputStream.read(buffer)) > EOF;) {
			dstOutputStream.write(buffer, 0, readLen);
		}
		dstOutputStream.flush();
	}
	
	public static void copy(InputStream srcInputStream, File dstFile) throws IOException {
		OutputStream dstOutputStream = new FileOutputStream(dstFile);
		copy(srcInputStream, dstOutputStream);
		closeQuietly(dstOutputStream);
	}
	
	public static void copy(InputStream srcInputStream, String dstFile) throws IOException {
		copy(srcInputStream, new File(dstFile));
	}
	
	public static void copy(Reader srcReader, Writer dstWriter) throws IOException {
		char[] buffer = new char[BUFFER_SIZE];
		for (int readLen; (readLen = srcReader.read(buffer)) > EOF;) {
			dstWriter.write(buffer, 0, readLen);
		}
		dstWriter.flush();
	}
	
	public static void copy(String srcStr, File dstFile) throws IOException {
		StringReader srcStrReader = new StringReader(srcStr);
		FileWriter dstFileWriter = new FileWriter(dstFile);
		copy(srcStrReader, dstFileWriter);
		closeQuietly(srcStrReader);
		closeQuietly(dstFileWriter);
	}
	
	public static void copy(URL url, File file) throws IOException {
		InputStream inputStream = url.openStream();
		copy(inputStream, file);
		closeQuietly(inputStream);
	}
	
	public static void copy(URL url, String file) throws IOException {
		copy(url, new File(file));
	}
}
