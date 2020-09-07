package com.github.smallru8.Secure2.config;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
	public enum ConfType{
		CLIENT,SERVER
	}
	
	private static String Path0 = "config/server/";
	private static String Path1 = "config/client/";
	
	/**
	 * 存 連線名稱/switch名稱
	 */
	public String confName;
	
	public Properties pro = new Properties();
	public boolean SQLFlag = false;//true = SQL, false = SQLite
	public String host = "ws://sample.ip";//ip, SQL host
	public String switchName = "switchName";
	public String userName = "userName";//user
	public String passwd = "passwd";//password
	public int port = 8787;//port
	
	private String confPath;
	
	/**
	 * 檢查config資料夾
	 */
	public Config() {
		if(!new File("config").exists())
			new File("config").mkdirs();
		if(!new File("config/server").exists())
			new File("config/server").mkdirs();
		if(!new File("config/client").exists())
			new File("config/client").mkdirs();
		if(!new File("config/bgv.conf").exists())
			try {
				new File("config/bgv.conf").createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	/**
	 * 存在就讀取 不存在就建立
	 * @param confName
	 * @param type
	 */
	public void setConf(String confName,ConfType type) {
		this.confName = confName;
		if(type == ConfType.CLIENT)
			confPath = Path1+confName+".conf";
		else
			confPath = Path0+confName+".conf";
		try {
			pro.load(new BufferedInputStream(new FileInputStream(confPath)));
		} catch (FileNotFoundException e) {
			if(type == ConfType.CLIENT) {
				pro.put("ip", host);
				pro.put("port", ""+port);
				pro.put("switch", switchName);
				pro.put("user", userName);
				pro.put("passwd", passwd);
			}else {
				pro.put("SQL", "false");
				pro.put("host", "jdbc:sqlite:config/Secure.db");
				pro.put("port", "3306");
				port = 3306;
				host = "jdbc:sqlite:config/Secure.db";
				pro.put("user", userName);
				pro.put("passwd", passwd);
			}
		    try {
		    	pro.store(new BufferedOutputStream(new FileOutputStream(confPath)),"Save Configs File.");
		    } catch (FileNotFoundException f) {
		        f.printStackTrace();
		    } catch (IOException i) {
		        i.printStackTrace();
		    }
		} catch (IOException i) {
		    i.printStackTrace();
		} 
		
		if(type == ConfType.CLIENT) {
			host = pro.getProperty("ip");
			port = Integer.parseInt(pro.getProperty("port"));
			switchName = pro.getProperty("switch");
			userName = pro.getProperty("user");
			passwd = pro.getProperty("passwd");
		}else {
			SQLFlag = pro.getProperty("SQL").equalsIgnoreCase("true");
			host = pro.getProperty("host");
			//port = Integer.parseInt(pro.getProperty("port"));
			userName = pro.getProperty("user");
			passwd = pro.getProperty("passwd");
		}
	}
	
}
