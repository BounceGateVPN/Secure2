package com.github.smallru8.Secure2.DH.Test;

import com.github.smallru8.Secure2.DH.DHRecver;
import com.github.smallru8.Secure2.DH.DHSender;

public class Test1 {
	/*
	 * DH流程
	 */
	public static void main( String[] args ) {
		DHSender ds = new DHSender();
		DHRecver dr = new DHRecver(ds.getPublicKey());
		ds.initAESKey(dr.getPublicKey());
		
		byte[] sendResult = ds.encrypt("Hello".getBytes());
        System.out.println("sendResult ："+new String(sendResult));
        
        byte[] receiverResult = dr.decryption(sendResult);
        System.out.println("receiverResult : "+new String (receiverResult));
		
	}
}
