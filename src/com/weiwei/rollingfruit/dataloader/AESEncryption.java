package com.weiwei.rollingfruit.dataloader;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AESEncryption {
	
	private static final String key = "Bar12345Bar12345"; 
	static Cipher cipher;
	private static SecretKeySpec secretKey;
	
	public AESEncryption() throws UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException{
		cipher = Cipher.getInstance("AES");
		secretKey = new SecretKeySpec(key.getBytes(), "AES");
	}
	
	public byte[] encrypt(byte[] clear) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
	    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	    byte[] encrypted = cipher.doFinal(clear);
	    return encrypted;
	}

	public byte[] decrypt(byte[] encrypted) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	    cipher.init(Cipher.DECRYPT_MODE, secretKey);
	    byte[] decrypted = cipher.doFinal(encrypted);
	    return decrypted;
	}
}
