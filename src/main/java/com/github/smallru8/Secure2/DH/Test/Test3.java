package com.github.smallru8.Secure2.DH.Test;

import java.sql.Connection;

import com.github.smallru8.Secure2.SQL.SQL;
import com.github.smallru8.Secure2.config.Config;

public class Test3 {
	/**
	 * SQLite測試
	 * @param args
	 */
	
	public static SQL sql = new SQL();
	
	public static void main( String[] args ) {
		Config cfg = new Config();
		cfg.setConf("default", Config.ConfType.SERVER);
		Connection conn = sql.getSQLConnection("default");//建表
		SQL.registered("default", conn, "test", "test0000");//註冊
		
	}
}
