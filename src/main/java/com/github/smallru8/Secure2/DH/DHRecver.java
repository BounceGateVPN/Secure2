package com.github.smallru8.Secure2.DH;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class DHRecver extends DH{

	public DHRecver(byte[] sendePublicKey) {
		if (Security.getProvider("BC") == null) { 
			Security.addProvider(new BouncyCastleProvider()); 
		}
		try {
			KeyFactory receiverKeyFactory = KeyFactory.getInstance("DH");
	        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(sendePublicKey);
	        PublicKey receivePublicKey = receiverKeyFactory.generatePublic(x509EncodedKeySpec);
	        DHParameterSpec dhParameterSpec = ((DHPublicKey)receivePublicKey).getParams();
	        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
	        receiverKeyPairGenerator.initialize(dhParameterSpec);
	        KeyPair keyPair = receiverKeyPairGenerator.generateKeyPair();
	        PrivateKey receiverPrivateKey = keyPair.getPrivate();
	        publicKeyEnc = keyPair.getPublic().getEncoded();
	        KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
	        receiverKeyAgreement.init(receiverPrivateKey);
	        receiverKeyAgreement.doPhase(receivePublicKey, true);
	        AESKey = receiverKeyAgreement.generateSecret("AES");
	        
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
