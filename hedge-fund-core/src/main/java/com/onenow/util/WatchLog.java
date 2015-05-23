package com.onenow.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.onenow.admin.NetworkConfig;

public class WatchLog {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public WatchLog() {	
	}
	
	public static String add(Level level, String message, String prepend, String postpend) {

		String s = "";

		s = textLogFormatter(level, message, prepend, postpend);
		
		fanout(level, s);
	
		return s;
				
	}

	private static String textLogFormatter(Level level, String message, String prepend, String postpend) {
		
		String s;
		String timeLog = "[" + ParseTime.getDashedNow() + "]";
		
		String ipLog = "";
		try {
			ipLog = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 	
		ipLog = "[" + ipLog + "]";
				
		String caller = new Exception().getStackTrace()[1].getClassName();
		// String calleeClassName = new Exception().getStackTrace()[0].getClassName();
		
		s = prepend + timeLog + " " + ipLog + "\t" + caller + "          "+ message + postpend;
		return s;
	}
	
	public static String addToLog(Level level, String message) {

		return add(level, message, "", "");

	}
	
	private static void fanout(Level level, String s) {

		// TODO: add to CloudWatch Logs here
		
		// print to console
		System.out.println(s);
		
		// print to files, not to console
		if(level.equals(Level.SEVERE)) {
			LOGGER.severe(s);
		}
		if(level.equals(Level.WARNING)) {
			LOGGER.warning(s);
		}
		if(level.equals(Level.INFO)) {
			LOGGER.info(s);
		}
		if(level.equals(Level.FINEST)) {
			LOGGER.finest(s);
		}
	}
		
	public static String getLogPath() {
		
		String s = "/var/log/";
		
		if(NetworkConfig.isMac()) {
			s = "/users/Shared/";
		}
		
		return s;
	}
}
