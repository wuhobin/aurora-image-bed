package com.wuhobin.common.util.security;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by HUFENG on 2018/3/30 0030.
 * DES ECB PKCS5Padding 对称加密 解密
 */
public class DESUtil {


	/**
	 * 获取des密钥
	 * @return 密钥
	 */
	public static String initKey(){
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			// 生成一个Key
			SecretKey generateKey = keyGenerator.generateKey();
			// 转变为字节数组
			byte[] encoded = generateKey.getEncoded();
			// 生成密钥字符串
			return Hex.encodeHexString(encoded);
		} catch (Exception e) {
			e.printStackTrace();
			return "密钥生成错误.";
		}
	}


	/**
	 * 加密数据
	 * @param encryptString
	 * @param encryptKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptDES(String encryptString, String encryptKey) throws Exception {

		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getKey(encryptKey), "DES"));
		byte[] encryptedData = cipher.doFinal(encryptString.getBytes("UTF-8"));
		return Base64.encodeBase64String(encryptedData);
	}

	/**
	 * key  不足8位补位
	 * @param keyRule
	 */
	public static byte[] getKey(String keyRule) {
		Key key = null;
		byte[] keyByte = keyRule.getBytes();
		// 创建一个空的八位数组,默认情况下为0
		byte[] byteTemp = new byte[8];
		// 将用户指定的规则转换成八位数组
		for (int i = 0; i < byteTemp.length && i < keyByte.length; i++) {
			byteTemp[i] = keyByte[i];
		}
		key = new SecretKeySpec(byteTemp, "DES");
		return key.getEncoded();
	}

	/***
	 * 解密数据
	 * @param decryptString
	 * @param decryptKey
	 * @return
	 * @throws Exception
	 */

	public static String decryptDES(String decryptString, String decryptKey) throws Exception {
		byte[] sourceBytes = Base64.decodeBase64(decryptString);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getKey(decryptKey), "DES"));
		byte[] decoded = cipher.doFinal(sourceBytes);
		return new String(decoded, "UTF-8");

	}

	public static void main(String[] args) throws Exception {
		String key = initKey();
		System.out.println("密钥：" + key);
		String clearText = "18772916901";
		//String key = "cmbtest1";//密钥
		System.out.println("明文："+clearText+"\n密钥："+key);
		String encryptText = encryptDES(clearText, key);
		System.out.println("加密后："+encryptText);
		String decryptText = decryptDES(encryptText, key);
		System.out.println("解密后："+decryptText);





	}

}
