package com.github.smallru8.Secure2.DH.Test;

import com.github.smallru8.Secure2.config.Config;

public class Test2 {
	/**
	 * config測試
	 * @param args
	 */
	public static void main( String[] args ) {
		Config cfg_cli = new Config();
		cfg_cli.setConf("client1", Config.ConfType.CLIENT);
		System.out.println(cfg_cli.host);
		
		Config cfg_ser = new Config();
		cfg_cli.setConf("server1", Config.ConfType.SERVER);
		System.out.println(cfg_ser.host);
	}
}
