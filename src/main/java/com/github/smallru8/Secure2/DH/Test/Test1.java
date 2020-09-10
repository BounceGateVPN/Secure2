package com.github.smallru8.Secure2.DH.Test;

import java.io.UnsupportedEncodingException;

import com.github.smallru8.Secure2.DH.DHRecver;
import com.github.smallru8.Secure2.DH.DHSender;

public class Test1 {
	/*
	 * DH流程
	 */
	public static void main( String[] args ) throws UnsupportedEncodingException {
		DHSender ds = new DHSender();
		DHRecver dr = new DHRecver(ds.getPublicKey());
		ds.initAESKey(dr.getPublicKey());
		
		String sendResult = new String(ds.encrypt("defaultSwitch\ntest\ntest".getBytes("UTF-8")),"UTF-8");
        System.out.println("sendResult ：" + sendResult);
        
        byte[] receiverResult = dr.decryption(sendResult.getBytes("UTF-8"));
        System.out.println("receiverResult : "+new String (receiverResult,"UTF-8"));
        
        byte[] sendResult2 = dr.encrypt("Hello".getBytes("UTF-8"));
        System.out.println("sendResult ："+new String(sendResult2));
        
        byte[] receiverResult2 = ds.decryption(sendResult2);
        System.out.println("receiverResult : "+new String (receiverResult2,"UTF-8"));
		
	}
}
