package com.github.smallru8.Secure2.DH;

import java.security.KeyPair;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;

public class DH {

	protected KeyPair keyPair;
	protected byte[] publicKeyEnc;
	protected SecretKey AESKey;
	protected Cipher cipher_encrypt;//AES256 CTR 加密
	protected Cipher cipher_decrypt;//AES256 CTR 解密
	
	public byte[] getPublicKey() {
		return publicKeyEnc;//給對方
	}
	
	/**
	 * 加密
	 * @param plaintext
	 * @return
	 */
	public byte[] encrypt(byte[] plaintext) {
		try {
			return cipher_encrypt.doFinal(plaintext);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 解密
	 * @param ciphertext
	 * @return
	 */
	public byte[] decryption(byte[] ciphertext) {
		try {
			return cipher_decrypt.doFinal(ciphertext);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
