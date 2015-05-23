package com.onenow.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.onenow.admin.NetworkConfig;

public class WatchLog {

	public WatchLog() {
		
	}

	public static String add(LogType type, String message, String prepend, String postpend) {

		String s = "";

		String timeLog = "[" + ParseTime.getDashedNow() + "]";
		
		String ipLog = "";
		try {
			ipLog = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 	
		ipLog = "[" + ipLog + "]";
		
		String typeLog = "[" + type + "]";
		
		String caller = new Exception().getStackTrace()[1].getClassName();
		// String calleeClassName = new Exception().getStackTrace()[0].getClassName();
		
		s = prepend + timeLog + " " + ipLog + " " + typeLog + "\t" + caller + "          "+ message + postpend;
		
		// TODO: add to CloudWatch Logs here
		System.out.println(s);
		
		getLogger().info(s);  
	
		return s;
				
	}
	
	public static String addToLog(LogType type, String message) {

		return add(type, message, "", "");

	}
	

	private static Logger getLogger() {
		
	    Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  

	    try {  

	        // This block configure the logger with handler and formatter
	    	// /Users/pablo/Downloads
	        fh = new FileHandler(getLogFile());  
//	         fh = new FileHandler("/Users/shared");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    
	    return logger;
	}
	
	private static String getLogFile() {
		
		String s = "/var/log/HedgeFund.log";
		
		if(NetworkConfig.isMac()) {
			s = "/users/Shared/HedgeFund.log";
		}
		
		return s;
	}
}
