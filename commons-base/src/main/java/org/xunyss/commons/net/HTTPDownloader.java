package org.xunyss.commons.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import io.xunyss.commons.io.IOUtils;

/**
 * 
 * @author XUNYSS
 */
public class HTTPDownloader {
	
	private static final int SC_OK = 200;
	
	private File downloadPath = new File(".");
	public Proxy proxy = Proxy.NO_PROXY;

	
	public void setDownloadPath(File path) {
		downloadPath = path;
		downloadPath.mkdirs();
	}
	
	public void setDownloadPath(String path) {
		setDownloadPath(new File(path));
	}
	
	public void setProxy(String proxyType, String ipAddress, int port) {
		proxy = new Proxy(Proxy.Type.valueOf(proxyType.toUpperCase()),
				new InetSocketAddress(ipAddress, port));
	}
	
	public String download(String downloadUrl, String fileName) throws IOException {
		HttpURLConnection httpConn = openConnection(downloadUrl);
		httpConn.setRequestMethod("GET");
		httpConn.setDoInput(true);
		httpConn.setDoOutput(false);

		int responseCode = httpConn.getResponseCode();
		if (responseCode == SC_OK) {
			InputStream httpInputStream = httpConn.getInputStream();
			File downFile = new File(downloadPath, fileName);
			
			try {
				IOUtils.copy(httpInputStream, downFile);
				return downFile.getPath();
			}
			finally {
				IOUtils.closeQuietly(httpInputStream);
			}
		}
		
		return null;
	}
	
	public String download(String downloadUrl) throws IOException {
		return download(downloadUrl, getFileName(downloadUrl));
	}
	
	private HttpURLConnection openConnection(String downloadUrl) throws IOException {
		URL url = new URL(downloadUrl);
		HttpURLConnection httpConn = (HttpURLConnection)
				(proxy == Proxy.NO_PROXY ? url.openConnection() : url.openConnection(proxy));
		
		return httpConn;
	}
	
	private static String getFileName(String url) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return url.substring(url.lastIndexOf('/'))
				.replace("/", "");
	}
}
