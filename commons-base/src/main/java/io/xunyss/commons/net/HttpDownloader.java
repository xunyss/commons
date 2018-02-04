package io.xunyss.commons.net;

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
public class HttpDownloader {
	
	/**
	 * TODO: listener 지원
	 * TODO: https 프로토콜 지원
	 */
	
	private File downloadPath = new File(".");
	private Proxy proxy;
	
	
	public HttpDownloader(Proxy.Type proxyType, String ipAddress, int port) {
		proxy = new Proxy(proxyType, new InetSocketAddress(ipAddress, port));
	}
	
	public HttpDownloader() {
		proxy = Proxy.NO_PROXY;
	}
	
	
	public void setDownloadPath(File path) {
		downloadPath = path;
		downloadPath.mkdirs();
	}
	
	public void setDownloadPath(String path) {
		setDownloadPath(new File(path));
	}
	
	
	public String download(String downloadUrl, String fileName) throws IOException {
		HttpURLConnection httpConn = openConnection(downloadUrl);
		httpConn.setRequestMethod(HttpMethod.GET.name());
		httpConn.setDoInput(true);
		httpConn.setDoOutput(false);
		httpConn.setUseCaches(false);

		int responseCode = httpConn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
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
		return download(downloadUrl, detectFileName(downloadUrl));
	}
	
	
	private HttpURLConnection openConnection(String downloadUrl) throws IOException {
		URL url = new URL(downloadUrl);
		String protocol = url.getProtocol();
		if ("https".equals(protocol)) {
			// TODO: https 프로토콜 지원
		}
		return (HttpURLConnection) (proxy == Proxy.NO_PROXY ? url.openConnection() : url.openConnection(proxy));
	}
	
	private static String detectFileName(String url) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return url.substring(url.lastIndexOf('/'))
				.replace("/", "");
	}
}
