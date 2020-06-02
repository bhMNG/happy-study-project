package com.happystudy.util;

import java.io.IOException;

import cn.hutool.core.codec.Base64;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;

public class CipherMachine {

	public static void newKey() throws IOException {
		byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
		CipherMachine.saveKey(key);
	}

	// 加密 base64 + md5 + 余9位添加顺序添加字母加密 + aes + 盐
	public static String encryption(String password) throws IOException {
		// base64加密
		String cryptStr = Base64.encode(password);
		// md5
		cryptStr = SecureUtil.md5(cryptStr);
		// 余9位
		String keypool = "asdfzxcas";
		int cryptSize = cryptStr.length();
		int keypoolSize = keypool.length();
		StringBuffer addStr = new StringBuffer();
		for (int i = 0; i < cryptSize; i++) {
			int charIndex = i % keypoolSize;
			addStr.append(keypool.charAt(charIndex));
		}
		cryptStr += addStr.toString();
	
		// //随机生成密钥
		byte[] key = CipherMachine.getKey();
		AES aes = SecureUtil.aes(key); // 构建
		byte[] encryptBytes = aes.encrypt(cryptStr); // 加密
		// 加盐
		String result = addSalt(encryptBytes);
		// 得到暗文
		return result;

	}

	/**
	 * 加盐处理
	 * 
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

	public static byte[] getKey() throws IOException {
		return (byte[]) HappyStudyIO.read("src/main/resources/static/key/secret_key.obj");
	}

	private static void saveKey(byte[] key) throws IOException {
		HappyStudyIO.write(key, "src/main/resources/static/key/secret_key.obj");
	}

}
