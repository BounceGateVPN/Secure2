package com.github.smallru8.Secure2.DH.Test;

import java.sql.Connection;

import com.github.smallru8.Secure2.SQL.SQL;

public class Test3 {
	/**
	 * SQLite測試
	 * @param args
	 */
	
	public static SQL sql = new SQL();
	
	public static void main( String[] args ) {
		Connection conn = sql.getSQLConnection("server1");//建表
		SQL.registered("server1", conn, "test", "test0000");//註冊
		
	}
}
