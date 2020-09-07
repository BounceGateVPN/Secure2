package com.github.smallru8.Secure2.SQL;

import java.security.Security;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import com.github.smallru8.util.RegularExpression;

/**
 * 
 * @author smallru8
 *
 */
public class SQL {
	//SQL field |Name|PASSWD|Session|
	private static String DEFAULT_SQLITE = "jdbc:sqlite:config/Secure.db"; 
	private String sqlstmt0 = "CREATE TABLE ";
	private String sqlstmt1 = "(Name VARCHAR(512),PASSWD VARCHAR(1024),Session VARCHAR(512), PRIMARY KEY (Name));";

	public SQL() {
		try {
			Security.addProvider(new BouncyCastleProvider());
			//Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			System.err.println("SQL driver not found.");
			e1.printStackTrace();
		}
	}
	
	/**
	 * get SQL
	 * @param confName
	 * @param host
	 * @param user
	 * @param passwd
	 * @return
	 */
	public Connection getSQLConnection(String confName,String host,String user,String passwd) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(host,user,passwd);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSQLConnection(confName,conn);
	}
	
	/**
	 * get SQLite
	 * @param confName
	 * @param host
	 * @return
	 */
	public Connection getSQLConnection(String confName) {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(DEFAULT_SQLITE);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getSQLConnection(confName,conn);
	}

	/**
	 * 檢查Table
	 * @param confName
	 * @param conn
	 * @return
	 */
	private Connection getSQLConnection(String confName,Connection conn) {
		Statement stmt = null;
		//檢查<confName> table是否存在
		try {
			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, confName, null);
			if (!tables.next()) {
				stmt = conn.createStatement();
				stmt.executeUpdate(sqlstmt0+confName+sqlstmt1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 註冊
	 * @param confName
	 * @param conn
	 * @param usrName
	 * @param passwd -> sha512(passwd)
	 * @return
	 */
	public static boolean registered(String confName,Connection conn,String usrName,String passwd) {
		if(!RegularExpression.isLetterDigitOnly(confName))//只包含英文數字
			return false;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT * FROM "+ confName +" WHERE Name = ?;");
			ps.setString(1, usrName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {	//Name重複
				return false;
			}
			ps = conn.prepareStatement("INSERT INTO "+confName+" (Name,PASSWD,Session)\n" + "VALUES (?, ?, 0);");
			ps.setString(1, usrName);
			ps.setString(2, passwd);
			int ret = ps.executeUpdate();
			ps.close();
			if(ret > 0)
				return true;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 刪除使用者
	 * @param confName
	 * @param conn
	 * @param usrName
	 * @return
	 */
	public static boolean removeUser(String confName,Connection conn,String usrName) {
		if(!RegularExpression.isLetterDigitOnly(confName))//只包含英文數字
			return false;
		try {
			PreparedStatement ps = conn.prepareStatement("DELETE FROM "+confName+" WHERE Name = ?;");
			ps.setString(1, usrName);
			int ret = ps.executeUpdate();
			ps.close();
			if(ret > 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 讀user的密碼 sha512(passwd)
	 * confName為switchName
	 * 無則回傳null
	 * @param confName
	 * @param conn
	 * @return
	 */
	public static String getPasswd(String confName,Connection conn,String usrName) {
		if(!RegularExpression.isLetterDigitOnly(confName))//只包含英文數字
			return null;
		String passwd = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT PASSWD FROM "+confName+" WHERE Name = ?;");
			ps.setString(1, usrName);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				passwd = rs.getString(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return passwd;
	}
	
	/**
	 * 更新密碼
	 * @param confName
	 * @param conn
	 * @param usrName
	 * @param passwd
	 * @return
	 */
	public static boolean changePasswd(String confName,Connection conn,String usrName,String newPasswd) {
		if(!RegularExpression.isLetterDigitOnly(confName))//只包含英文數字
			return false;
		try {
			PreparedStatement ps = conn.prepareStatement("UPDATE "+confName+" SET PASSWD = ? WHERE Name = ?;");
			ps.setString(1, newPasswd);
			ps.setString(2, usrName);
			int ret = ps.executeUpdate();
			ps.close();
			if(ret>0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
