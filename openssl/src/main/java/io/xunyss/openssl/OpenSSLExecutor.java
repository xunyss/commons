package io.xunyss.openssl;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import org.xunyss.commons.util.ArchiveUtils;
import org.xunyss.commons.util.ResourceUtils;

import io.xunyss.commons.exec.ProcessExecutor;
import io.xunyss.commons.io.FileUtils;
import io.xunyss.commons.io.NullOutputStream;
import io.xunyss.commons.lang.SystemUtils;

/**
 * https://wiki.openssl.org/index.php/Binaries
 * https://indy.fulgan.com/SSL/
 * openssl-1.0.2g-i386-win32
 * 
 * @author XUNYSS
 */
public class OpenSSLExecutor {
	
	/**
	 *
	 */
	private static class SingletonHolder {
		// singleton object
		private static final OpenSSLExecutor instance = new OpenSSLExecutor();
	}
	
	/**
	 *
	 * @return
	 */
	public static OpenSSLExecutor getInstance() {
		return SingletonHolder.instance;
	}
	
	
	//----------------------------------------------------------------------------------------------
	
	private static final String RESOURCE_BINARY_PATH = "/io/xunyss/openssl/binary";
	private static final String EXTRACT_DIRECTORY = "io_xunyss_openssl_bin";
	
	private String binName = "openssl";		// default executable binary name
	private boolean initialized = false;	// ..
	
	
	private OpenSSLExecutor() {
		if (!(initialized = selfTest())) {
			if (SystemUtils.IS_OS_WINDOWS) {	// windows
				temporaryInstall("win32", "openssl.exe");
			}
			// TODO linux, unix, ...
			initialized = selfTest();
		}
	}
	
	private boolean selfTest() {
		try {
			// openssl version
			return 0 == exec(NullOutputStream.NULL_OUTPUT_STREAM, "version");
		}
		catch (IOException ioe) {
			return false;
		}
	}
	
	private void temporaryInstall(String subPackage, String simpleBinaryName) {
		final String srcResDirPath = RESOURCE_BINARY_PATH + FileUtils.RESOURCE_PATH_SEPARATOR_CHAR + subPackage;
		final File dstDir = new File(FileUtils.getTempDirectoryPath(), EXTRACT_DIRECTORY);
		
		// real binary file full path
		binName = dstDir.getPath() + FileUtils.FILE_SEPARATOR + simpleBinaryName;
		
		try {
			// extract resource to temporary directory
			extractResources(srcResDirPath, dstDir);
		}
		catch (IOException ioe) {
			// not not throw any exception
			return;
		}
		finally {
			// remove temporary binaries when system exit
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					FileUtils.deleteDirectoryQuietly(dstDir);
				}
			});
		}
	}
	
	private void extractResources(String srcResDirPath, File dstDir) throws IOException {
		final URL srcResDirUrl = getClass().getResource(srcResDirPath);
		
		// when this is running at '.class'
		if (ResourceUtils.isFileURL(srcResDirUrl)) {
			FileUtils.copyDirectory(new File(srcResDirUrl.getFile()), dstDir);
		}
		// when this is running in '.jar'
		else if (ResourceUtils.isJarURL(srcResDirUrl)) {
			ArchiveUtils.unjar(ResourceUtils.getJarFileURL(srcResDirUrl), srcResDirPath, dstDir);
		}
		// other
		else {
			throw new IOException("bad URL: " + srcResDirPath);
		}
	}
	
	
	public int exec(OutputStream outputStream, String... args) throws IOException {
		if (!initialized) {
			throw new IOException("openSSL is not initialized");
		}
		//------------------------------------------------------------------------------------------
		try {
			ProcessExecutor processExecutor = new ProcessExecutor();
			return processExecutor.execute(args);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new IOException();
		}
	}
}
