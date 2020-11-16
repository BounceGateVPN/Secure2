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
		CLIENT,SERVER,ROUTER,INTERFACE
	}
	
	private static String Path0 = "config/server/";
	private static String Path1 = "config/client/";
	private static String Path2 = "config/router/";
	private static String Path3 = "config/interface/";
	
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
	public String ip = "192.168.87.1"; //router's ip
	public String netmask = "255.255.255.0";
	public String routingTable = "";	//gateway feild is switch or interface
	
	//for RouterInterface
	public String InterfaceIP = "192.168.87.2";
	public String InterfaceNetmask = "255.255.255.0";
	public String InterfaceGateway = "192.168.87.1";
	
	private String confPath;
	
	/**
	 * 檢查config資料夾
	 */
	public Config() {
		if(!new File("config").exists())
			new File("config").mkdirs();
		if(!new File("config/router").exists())
			new File("config/router").mkdirs();
		if(!new File("config/interface").exists())
			new File("config/interface").mkdirs();
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
		else if(type == ConfType.SERVER)
			confPath = Path0+confName+".conf";
		else if(type == ConfType.ROUTER)
			confPath = Path2+confName+".conf";
		else
			confPath = Path3+confName+".conf";
		try {
			pro.load(new BufferedInputStream(new FileInputStream(confPath)));
		} catch (FileNotFoundException e) {
			if(type == ConfType.CLIENT || type == ConfType.INTERFACE) {
				pro.put("ip", host);
				pro.put("port", ""+port);
				pro.put("switch", switchName);
				pro.put("user", userName);
				pro.put("passwd", passwd);
				if(type == ConfType.INTERFACE) {
					pro.put("InterfaceIP", InterfaceIP);
					pro.put("InterfaceNetmask", InterfaceNetmask);
					pro.put("InterfaceGateway", InterfaceGateway);
				}
			}else if(type == ConfType.SERVER) {
				pro.put("SQL", "false");
				pro.put("host", "jdbc:sqlite:config/Secure.db");
				pro.put("port", "3306");
				port = 3306;
				host = "jdbc:sqlite:config/Secure.db";
				pro.put("user", userName);
				pro.put("passwd", passwd);
				pro.put("switch", switchName);
			}else {
				pro.put("ip", ip);
				pro.put("netmask", netmask);
				pro.put("routingTable", routingTable);
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
		
		if(type == ConfType.CLIENT || type == ConfType.INTERFACE ) {
			host = pro.getProperty("ip");
			port = Integer.parseInt(pro.getProperty("port"));
			switchName = pro.getProperty("switch");
			userName = pro.getProperty("user");
			passwd = pro.getProperty("passwd");
			if(type == ConfType.INTERFACE) {
				InterfaceIP = pro.getProperty("InterfaceIP");
				InterfaceNetmask = pro.getProperty("InterfaceNetmask");
				InterfaceGateway = pro.getProperty("InterfaceGateway");
			}
		}else if(type == ConfType.SERVER) {
			SQLFlag = pro.getProperty("SQL").equalsIgnoreCase("true");
			host = pro.getProperty("host");
			//port = Integer.parseInt(pro.getProperty("port"));
			userName = pro.getProperty("user");
			passwd = pro.getProperty("passwd");
			switchName = pro.getProperty("switch");
		}else {
			ip = pro.getProperty("ip");
			netmask = pro.getProperty("netmask");
			routingTable = pro.getProperty("routingTable");
		}
		
	}
	
	public void saveConf() {
		try {
			pro.store(new BufferedOutputStream(new FileOutputStream(confPath)),"Save Configs File.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
