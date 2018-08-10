package io.xunyss.commons.exec.support;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import io.xunyss.commons.exec.ProcessExecutor;

/**
 * Unit tests for the LineReadHandler class.
 * 
 * @author XUNYSS
 */
public class LineReadHandlerTest {
	
	@Test
	public void test() throws Exception {
		ProcessExecutor processExecutor = new ProcessExecutor();
		processExecutor.setStreamHandler(new LineReadHandler() {
			@Override
			public void processLine(String line) {
				System.out.println(line);
			}

			@Override
			public void pre() {
				System.out.println("@@@@@@@@@@@@@@@");
			}

			@Override
			public void post() {
				System.out.println("!!!!!!!!!!!!!!");
			}
		});
		
		processExecutor.execute("cmd /c dir");
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	static byte[] key = hexToByteArray("da39a3ee5e6b4b0d3255bfef95601890da39a3ee5e6b4b0d3255bfef95601890");
	static byte[] iv = hexToByteArray("7b768186c886d4a96b6ca542c1aed8ac");
	static SecretKeySpec secretKeySpec;
	static IvParameterSpec ivParameterSpec;
	static {
		try {
			secretKeySpec = new SecretKeySpec((key), "AES");
			ivParameterSpec = new IvParameterSpec(iv);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static String enc(String plain) throws Exception {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		c.init(Cipher.ENCRYPT_MODE,
				secretKeySpec,
				ivParameterSpec
		);
		byte[] encb = c.doFinal(plain.getBytes());
		return Base64.encodeBase64String(encb);
	}
	
	static String dec(String cipher) throws Exception {
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		c.init(Cipher.DECRYPT_MODE,
				secretKeySpec,
				ivParameterSpec
		);
		byte[] decb = Base64.decodeBase64(cipher);
		return new String(c.doFinal(decb));
	}

	// hex to byte[]
	public static byte[] hexToByteArray(String hex) { if (hex == null || hex.length() == 0) { return null; } byte[] ba = new byte[hex.length() / 2]; for (int i = 0; i < ba.length; i++) { ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16); } return ba; }
	// byte[] to hex
	public static String byteArrayToHex(byte[] ba) { if (ba == null || ba.length == 0) { return null; } StringBuffer sb = new StringBuffer(ba.length * 2); String hexNumber; for (int x = 0; x < ba.length; x++) { hexNumber = "0" + Integer.toHexString(0xff & ba[x]); sb.append(hexNumber.substring(hexNumber.length() - 2)); } return sb.toString(); }
	
	@Test
	public void aes() throws Exception {
		String e = enc("가나다라마바사123456789ABCDEFG");
		System.out.println(e);
		String d = dec("ycd4QG+PdgzC8zte/0wy667t50xc2ZBaFLANii13sj1SPjvMhmysJB/PSQ4cVsyA");
		System.out.println(d);
	}
}
