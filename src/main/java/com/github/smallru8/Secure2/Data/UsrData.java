package com.github.smallru8.Secure2.Data;

import com.github.smallru8.Secure2.DH.DH;
import com.github.smallru8.Secure2.DH.DHRecver;
import com.github.smallru8.Secure2.DH.DHSender;

public class UsrData {
	
	public boolean readyFlag = false;
	
	public String name;//user name
	public String passwd;//sha512(passwd)
	public String destSwitchName;//目標Switch名稱
	public DH dh;//key exchange
	
	//client only
	public String sessionName;//這個連線的名稱
	public String IPaddr;//server ip
	public int port;//server port

	/**
	 * 發送方(client端)
	 */
	public UsrData() {
		dh = new DHSender();
	}
	
	/**
	 *接收方(server端)
	 *收Client傳來的publickey
	 * @param dh
	 */
	public UsrData(byte[] publicKey) {
		dh = new DHRecver(publicKey);
	}
	
	/**
	 * 設定使用者名稱、密碼
	 * @param name
	 * @param passwd
	 */
	public void setNamePasswd(String name,String passwd) {
		this.name = name;
		this.passwd = passwd;
	}
	
}
