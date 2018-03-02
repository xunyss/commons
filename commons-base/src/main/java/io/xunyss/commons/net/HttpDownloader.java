package io.xunyss.commons.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import io.xunyss.commons.io.FileUtils;

/**
 * 
 * @author XUNYSS
 */
public class HttpDownloader {
	
	/**
	 * TODO: listener 지원
	 * TODO: https 프로토콜 지원
	 */
	
	private File downloadDir = new File(".");
	private Proxy proxy;
	
	
	public HttpDownloader(Proxy.Type proxyType, String ipAddress, int port) {
		proxy = new Proxy(proxyType, new InetSocketAddress(ipAddress, port));
	}
	
	public HttpDownloader() {
		proxy = Proxy.NO_PROXY;
	}
	
	
	public void setDownloadPath(File path) throws IOException {
		FileUtils.makeDirectory(path);
		downloadDir = path;
	}
	
	public void setDownloadPath(String path) throws IOException {
		setDownloadPath(new File(path));
	}
	
	public String getDownloadPath() {
		try {
			return downloadDir.getCanonicalPath();
		}
		catch (IOException ex) {
			return downloadDir.getAbsolutePath();
		}
	}
	
	
	public String download(String downloadUrl, String fileName) throws IOException {
		HttpURLConnection httpConn = openConnection(downloadUrl);
		httpConn.setRequestMethod(HttpMethod.GET);
		httpConn.setDoInput(true);
		httpConn.setDoOutput(false);
		httpConn.setUseCaches(false);

		int responseCode = httpConn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			File downFile = new File(downloadDir, fileName);
//			InputStream httpInputStream = httpConn.getInputStream();
//			try {
//				FileUtils.copy(httpInputStream, downFile);
//				return downFile.getPath();
//			}
//			finally {
//				IOUtils.closeQuietly(httpInputStream);
//			}
			// 2018.03.02 XUNYSS
			// convert above statement to Java7 'try-with-resources' statement
			try (InputStream httpInputStream = httpConn.getInputStream()) {
				FileUtils.copy(httpInputStream, downFile);
				return downFile.getPath();
				
			}
		}
		
		return null;
	}
	
	public String download(String downloadUrl) throws IOException {
		return download(downloadUrl, detectFileName(downloadUrl));
	}
	
	
	private HttpURLConnection openConnection(String downloadUrl) throws IOException {
		URL url = new URL(downloadUrl);
		HttpURLConnection httpConnection = (HttpURLConnection) (proxy == Proxy.NO_PROXY ? url.openConnection() : url.openConnection(proxy));
		
		if (Protocol.HTTPS.equals(url.getProtocol())) {
			TrustAllCerts.setToConnection(httpConnection);
		}
		return httpConnection;
	}
	
	private static String detectFileName(String url) {
		if (url.endsWith("/")) {
			url = url.substring(0, url.length() - 1);
		}
		return url.substring(url.lastIndexOf('/'))
				.replace("/", "");
	}
}
