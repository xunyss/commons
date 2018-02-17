package io.xunyss.commons.net;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * @author XUNYSS
 */
public class TrustAllCerts {
	
	/**
	 * 
	 */
	private static final String PROTOCOL_HTTPS = Protocol.HTTPS;
	
	private static SSLContext DEFAULT_SSL_CONTEXT;
	private static SSLSocketFactory DEFAULT_SSL_SOCKET_FACTORY;
	static {
		// SSLContext.getDefault()
		try {
			DEFAULT_SSL_CONTEXT = SSLContext.getDefault();
		}
		catch (NoSuchAlgorithmException ex) {
			DEFAULT_SSL_CONTEXT = null;
		}
		// HttpsURLConnection.getDefaultSSLSocketFactory();
		DEFAULT_SSL_SOCKET_FACTORY = HttpsURLConnection.getDefaultSSLSocketFactory();
	}
	
	
	/**
	 * Constructor.
	 */
	private TrustAllCerts() {
		// cannot create instance
	}
	
	/**
	 * Trust managers that does not validate certificate chains.
	 */
	public static final TrustManager[] TRUST_MANAGERS = new TrustManager[] {
		new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
			@Override
			public X509Certificate[] getAcceptedIssuers() {
			//	return new X509Certificate[0];
				return null;
			}
		}
	};
	
	/**
	 * 
	 */
	public static final SSLContext SSL_CONTEXT = _initSSLContext();
	private static final SSLContext _initSSLContext() {
		try {
		//	SSLContext sslContext = SSLContext.getInstance("SSL");
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, TRUST_MANAGERS, new SecureRandom());
			return sslContext;
		}
		catch (NoSuchAlgorithmException | KeyManagementException ex) {
			return null;
		}
	}
	
	/**
	 * 
	 */
	public static final HostnameVerifier HOSTNAME_VERIFIER = new HostnameVerifier() {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	
	
	/**
	 * 
	 */
	public static final void setDefaultSSLContext() {
		SSLContext.setDefault(SSL_CONTEXT);
	}
	
	/**
	 * 
	 */
	public static final void restoreDefaultSSLContext() {
		SSLContext.setDefault(DEFAULT_SSL_CONTEXT);
	}
	
	/**
	 * 
	 */
	public static final void setDefaultSSLSocketFactory() {
		HttpsURLConnection.setDefaultSSLSocketFactory(SSL_CONTEXT.getSocketFactory());
	}
	
	/**
	 * 
	 */
	public static final void restoreDefaultSSLSocketFactory() {
		HttpsURLConnection.setDefaultSSLSocketFactory(DEFAULT_SSL_SOCKET_FACTORY);
	}
	
	/**
	 * 
	 * @param connection
	 */
	public static final void setToConnection(HttpURLConnection connection) {
		if (connection != null) {
			URL url = connection.getURL();
			if (PROTOCOL_HTTPS.equals(url.getProtocol())) {
				HttpsURLConnection httpsConn = (HttpsURLConnection) connection;
				httpsConn.setSSLSocketFactory(SSL_CONTEXT.getSocketFactory());
				httpsConn.setHostnameVerifier(HOSTNAME_VERIFIER);
			}
		}
	}
}
