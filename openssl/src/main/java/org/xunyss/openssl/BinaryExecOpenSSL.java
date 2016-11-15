package org.xunyss.openssl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.xunyss.commons.util.ArchiveUtils;
import org.xunyss.commons.util.FileUtils;
import org.xunyss.commons.util.IOUtils;
import org.xunyss.commons.util.ObjectUtils;
import org.xunyss.commons.util.ResourceUtils;

/**
 * https://wiki.openssl.org/index.php/Binaries
 * https://indy.fulgan.com/SSL/
 * openssl-1.0.2g-i386-win32
 * 
 * @author XUNYSS
 */
public class BinaryExecOpenSSL {

	private static class SingletoneHolder {
		private static final BinaryExecOpenSSL instance = new BinaryExecOpenSSL();
	}
	
	public static BinaryExecOpenSSL getInstance() {
		return SingletoneHolder.instance;
	}
	
	
	private static final String BINARY_RESOURCE_PATH = "/com/xunyss/openssl/binary";
	
	private String binaryResourcePath;
	private File extractDir;
	
	private String binName;
	
	private BinaryExecOpenSSL() {
		binaryResourcePath = BINARY_RESOURCE_PATH + FileUtils.FILE_SEPARATOR + getOS();
		extractDir = new File(FileUtils.getTempDir(), "com_xunyss_openssl_bin");
		
		binName = extractDir.getPath() + File.separator + getBinName();
		
		try {
			// extract resource to temporary directory
			extractResources();
			
			// remove temporary binaries when system exit
			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					FileUtils.deleteDirectoryQuietly(extractDir);
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getOS() {
		return "win32";
	}
	
	private String getBinName() {
		return "openssl.exe";
	}

	private void extractResources() throws Exception {
		URL binResurl = ResourceUtils.decodeURL(getClass().getResource(binaryResourcePath));
		
		// when this is running at '.class'
		if (ResourceUtils.isFileURL(binResurl)) {
			FileUtils.copyDirectory(new File(binResurl.getFile()), extractDir);
		}
		// when this is running in '.jar'
		else if (ResourceUtils.isJarURL(binResurl)) {
			ArchiveUtils.unjar(ResourceUtils.getJarFileURL(binResurl)
					, binaryResourcePath, extractDir);
		}
	}

	public void exec(OutputStream outputStream, String... args) throws IOException {
		String[] command = ObjectUtils.add(binName, args);
		
		try {
			Process process = Runtime.getRuntime().exec(command);

			IOUtils.closeQuietly(process.getOutputStream());			// stdout
			IOUtils.closeQuietly(process.getErrorStream());				// stderr

			InputStream processInputStream = process.getInputStream();	// stdin
			IOUtils.copy(processInputStream, outputStream);
			IOUtils.closeQuietly(processInputStream);
			
			process.waitFor();
			process.destroy();
		}
		catch (Exception e) {
			throw new IOException("openSSL execution fail", e);
		}
	}
}
