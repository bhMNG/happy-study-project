package com.happystudy.util;

import java.io.IOException;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

public class CipherMachine {

	public static void newKey() throws IOException{
		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		CipherMachine.saveKey(key);
	}
	
	/**
	 * 加盐处理
	 * @param encryptBytes
	 * @return
	 */
	public static String addSalt(byte[] encryptBytes) {
		StringBuffer buffer = new StringBuffer();
		for (byte b : encryptBytes) {
			int number = b & 0xff;
			String str = Integer.toHexString(number);
			if (str.length() == 1) {
				buffer.append(0);
			}
			buffer.append(str);
		}
		return buffer.toString();
	}
	
	public static byte[] getKey() throws IOException{
		return (byte[]) HappyStudyIO.read("src/main/resources/static/key/secret_key.obj");
	}
	private static void saveKey(byte[] key) throws IOException{
		HappyStudyIO.write(key, "src/main/resources/static/key/secret_key.obj");
	}
	
}
