package com.github.smallru8.Secure2.DH;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DHSender extends DH{
	
	public DHSender() {
		if (Security.getProvider("BC") == null) { 
			Security.addProvider(new BouncyCastleProvider()); 
		}
		try {
			KeyPairGenerator sendKeyPairGenerator = KeyPairGenerator.getInstance("DH");
	        sendKeyPairGenerator.initialize(512);
	        keyPair = sendKeyPairGenerator.generateKeyPair();
	        publicKeyEnc = keyPair.getPublic().getEncoded();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 收 接收方public key
	 * @param recvPublicKey
	 */
	public void initAESKey(byte[] recvPublicKey) {
		try {
			KeyFactory sendKeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(recvPublicKey);
	        PublicKey sendPublicKey = sendKeyFactory.generatePublic(x509EncodedKeySpec);       
	        KeyAgreement sendKeyAgreement = KeyAgreement.getInstance("DH");
	        sendKeyAgreement.init(keyPair.getPrivate());
	        sendKeyAgreement.doPhase(sendPublicKey, true);  
	        AESKey = sendKeyAgreement.generateSecret("AES");//发送方Key
	        
	        MessageDigest md5 = MessageDigest.getInstance("MD5");
	        md5.update(AESKey.getEncoded());
			IvParameterSpec ips = new IvParameterSpec(md5.digest());
			cipher_encrypt = Cipher.getInstance("AES/CTR/NoPadding", "BC");
			cipher_decrypt = Cipher.getInstance("AES/CTR/NoPadding", "BC");
	        cipher_encrypt.init(Cipher.ENCRYPT_MODE, AESKey, ips);
			cipher_decrypt.init(Cipher.DECRYPT_MODE, AESKey, ips);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
