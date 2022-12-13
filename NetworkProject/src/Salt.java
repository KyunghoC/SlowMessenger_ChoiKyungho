package client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Salt {
	public static String[] encrypt(String pwd) {
		Salt en = new Salt();

//		System.out.println("pwd : " + pwd);

		// Salt 생성
		String salt = en.getSalt();
//		System.out.println("salt : " + salt);

		// 최종 pwd 생성
		String res = en.getEncrypt(pwd, salt);
		String[] tmpStrings = new String[2];
		tmpStrings[0] = salt;
		tmpStrings[1] = res;
		return tmpStrings;
	}

	public static String validate(String pwd, String salt) {
		Salt en = new Salt();

//		System.out.println("pwd : " + pwd);

		String res = en.getEncrypt(pwd, salt);
		return res;
	}

	public String getSalt() {

		// 1. Random, byte 객체 생성
		SecureRandom r = new SecureRandom();
		byte[] salt = new byte[20];

		// 2. 난수 생성
		r.nextBytes(salt);

		// 3. byte To String (10진수의 문자열로 변경)
		StringBuffer sb = new StringBuffer();
		for (byte b : salt) {
			sb.append(String.format("%02x", b));
		}
		;

		return sb.toString();
	}

	public String getEncrypt(String pwd, String salt) {

		String result = "";
		try {
			// 1. SHA256 알고리즘 객체 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");

			// 2. pwd와 salt 합친 문자열에 SHA 256 적용
//			System.out.println("pwd + salt 적용 전 : " + pwd + salt);
			md.update((pwd + salt).getBytes());
			byte[] pwdsalt = md.digest();

			// 3. byte To String (10진수의 문자열로 변경)
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}

			result = sb.toString();
//			System.out.println("pwd + salt 적용 후 : " + result);

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
}
