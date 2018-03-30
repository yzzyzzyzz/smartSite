package com.ztjs.smartSite.common;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.commons.lang3.Validate;



public class HttpClientSSLUtils {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";
	private static SecureRandom random = new SecureRandom();

	public static byte[] md5(byte[] input) {
		return digest(input, "MD5", (byte[]) null, 1);
	}

	public static byte[] md5(byte[] input, int iterations) {
		return digest(input, "MD5", (byte[]) null, iterations);
	}

	public static byte[] sha1(byte[] input) {
		return digest(input, "SHA-1", (byte[]) null, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, "SHA-1", salt, 1);
	}

	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, "SHA-1", salt, iterations);
	}

	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest e = MessageDigest.getInstance(algorithm);
			if (salt != null) {
				e.update(salt);
			}

			byte[] result = e.digest(input);

			for (int i = 1; i < iterations; ++i) {
				e.reset();
				result = e.digest(result);
			}

			return result;
		} catch (GeneralSecurityException arg6) {
			throw new RuntimeException(arg6);
		}
	}

	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", (long) numBytes);
		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}

	public static byte[] md5(InputStream input) throws IOException {
		return digest(input, "MD5");
	}

	public static byte[] sha1(InputStream input) throws IOException {
		return digest(input, "SHA-1");
	}

	private static byte[] digest(InputStream input, String algorithm) throws IOException {
		try {
			MessageDigest e = MessageDigest.getInstance(algorithm);
			short bufferLength = 8192;
			byte[] buffer = new byte[bufferLength];

			for (int read = input.read(buffer, 0, bufferLength); read > -1; read = input.read(buffer, 0,
					bufferLength)) {
				e.update(buffer, 0, read);
			}

			return e.digest();
		} catch (GeneralSecurityException arg5) {
			throw new RuntimeException(arg5);
		}
	}

	public static final String md5(String s) {
		char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

		try {
			MessageDigest e = MessageDigest.getInstance("MD5");

			try {
				e.update(s.getBytes("UTF-8"));
			} catch (UnsupportedEncodingException arg8) {
				e.update(s.getBytes());
			}

			byte[] md = e.digest();
			int j = md.length;
			char[] str = new char[j * 2];
			int k = 0;

			for (int i = 0; i < j; ++i) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 15];
				str[k++] = hexDigits[byte0 & 15];
			}

			return (new String(str)).toUpperCase();
		} catch (Exception arg9) {
			return null;
		}
	}

	public static final String buildToken(String url, String paramJson, String secret) {
		String tempUrl = null;
		if (url.contains("https://")) {
			tempUrl = url.substring("https://".length());
		} else {
			tempUrl = url.substring("http://".length());
		}

		int index = tempUrl.indexOf("/");
		String URI = tempUrl.substring(index);
		String[] ss = URI.split("\\?");
		return ss.length > 1 ? md5(ss[0] + ss[1] + secret) : md5(ss[0] + paramJson + secret);
	}


	private static HttpClient client = null;

	protected static final Integer DEFAULT_CONNECTION_TIME_OUT = Integer.valueOf(100000);

	protected static final Integer DEFAULT_SOCKET_TIME_OUT = Integer.valueOf(200000);
	protected static final String DEFAULT_CHAR_SET = "UTF-8";

	public static String doPost(String url, String jsonText) throws Exception {
		HttpClient client = null;
		HttpPost post = new HttpPost(url);
		try {
			if ((jsonText != null) && (!(jsonText.isEmpty()))) {
				StringEntity entity = new StringEntity(jsonText, ContentType.APPLICATION_JSON);
				post.setEntity(entity);
			}

			RequestConfig.Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
			customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
			post.setConfig(customReqConf.build());
			HttpResponse res = null;
			if (url.startsWith("https")) {
				client = createSSLInsecureClient();
				res = client.execute(post);
			} else {
				client =  createSSLInsecureClient();
				res = client.execute(post);
			}
			String str = IOUtils.toString(res.getEntity().getContent(), "UTF-8");

			return str;
		} finally {
			post.releaseConnection();
			if ((url.startsWith("https")) && (client != null) && (client instanceof CloseableHttpClient))
				((CloseableHttpClient) client).close();
		}
	}

	public static String doGet(String url) throws Exception {
		HttpClient client = null;
		HttpGet get = new HttpGet(url);
		String result = "";
		try {
			RequestConfig.Builder customReqConf = RequestConfig.custom();
			customReqConf.setConnectTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
			customReqConf.setSocketTimeout(DEFAULT_CONNECTION_TIME_OUT.intValue());
			get.setConfig(customReqConf.build());
			HttpResponse res = null;
			if (url.startsWith("https")) {
				client = createSSLInsecureClient();
				res = client.execute(get);
			} else {
				client = client;
				res = client.execute(get);
			}
			result = IOUtils.toString(res.getEntity().getContent(), "UTF-8");
		} finally {
			get.releaseConnection();
			if ((url.startsWith("https")) && (client != null) && (client instanceof CloseableHttpClient)) {
				((CloseableHttpClient) client).close();
			}
		}
		return result;
	}

	private static CloseableHttpClient createSSLInsecureClient() throws GeneralSecurityException {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (GeneralSecurityException e) {
			throw e;
		}
	}


	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(128);
		cm.setDefaultMaxPerRoute(128);
		client = HttpClients.custom().setConnectionManager(cm).build();
	}
}