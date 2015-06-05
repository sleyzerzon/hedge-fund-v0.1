package com.onenow.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.onenow.admin.NetworkConfig;

public class Watchr {

	// http://www.vogella.com/tutorials/Logging/article.html
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Watchr() {	
	}
	
	public static String log(Level level, String message, String prepend, String postpend) {

		String machineMessage = machineTextLogFormatter(message);

		fanoutLog(level, prepend+message+postpend, machineMessage);
	
		return machineMessage;
				
	}
	
	public static String log(Level level, String message) {

		return log(level, message, "", "");

	}
	
	public static void log(String log) {
		log(Level.INFO, log);		
	}

	private static String machineTextLogFormatter(String message) {
		
		String s = "";
		
		String ipLog = "";
		try {
			ipLog = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 	
		ipLog = "[" + ipLog + "]";
				
		String caller = new Exception().getStackTrace()[3].getClassName() + " -> " + 
						new Exception().getStackTrace()[2].getClassName() + " -> ";
		
		s = " " + ipLog + "\t" + caller + "     "+ message;

		return s;
	}

	private static void fanoutLog(Level level, String message, String formattedMessage) {

		// TODO: add to CloudWatch Logs here

		// don't print individual chars
		if(message.length()<2) {
			return;
		}
		
		// print to console
		System.out.println(message);
		
		// print to files, not to console
		if(level.equals(Level.SEVERE)) {
			LOGGER.severe(formattedMessage);
		}
		if(level.equals(Level.WARNING)) {
			LOGGER.warning(formattedMessage);
		}
		if(level.equals(Level.INFO)) {
			LOGGER.info(formattedMessage);
		}
		else {
			LOGGER.finest(formattedMessage);
		}
	}
		
	public static String getLogPath() {
		
		String s = "/tmp/";
		
		if(NetworkConfig.isMac()) {
			s = "/users/Shared/";
		}
		
		return s;
	}

}
