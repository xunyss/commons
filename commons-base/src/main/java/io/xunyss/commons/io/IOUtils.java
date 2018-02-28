package io.xunyss.commons.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * I/O utilities.
 *
 * @author XUNYSS
 */
public final class IOUtils {
	
	/**
	 * Represents the end-of-file.
	 */
	public static final int EOF = -1;
	
	/**
	 * Default buffer size.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 8;
	
	
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
	 * Copy contents from an InputStream to an Writer.
	 * 
	 * @param srcInputStream input
	 * @param dstWriter output
	 * @return the number of characters copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(InputStream srcInputStream, Writer dstWriter) throws IOException {
		return copy(new InputStreamReader(srcInputStream), dstWriter);
	}
	
	/**
	 * Copy contents from an Reader to an OutputStream.
	 * 
	 * @param srcReader input
	 * @param dstOutputStream output
	 * @return the number of characters copied
	 * @throws IOException if an I/O error occurs
	 */
	public static int copy(Reader srcReader, OutputStream dstOutputStream) throws IOException {
		return copy(srcReader, new OutputStreamWriter(dstOutputStream));
	}
	
	/**
	 * Get the contents of a {@code Reader} as a String.
	 * 
	 * @param srcReader input
	 * @return the output string
	 * @throws IOException if an I/O error occurs
	 */
	public static String toString(Reader srcReader) throws IOException {
		try (Writer writer = new StringWriter()) {
			copy(srcReader, writer);
			return writer.toString();
		}
	}
	
	/**
	 * Get the contents of a {@code InputStream} as a String.
	 * 
	 * @param srcInputStream input
	 * @return the output string
	 * @throws IOException if an I/O error occurs
	 */
	public static String toString(InputStream srcInputStream) throws IOException {
		return toString(new InputStreamReader(srcInputStream));
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
