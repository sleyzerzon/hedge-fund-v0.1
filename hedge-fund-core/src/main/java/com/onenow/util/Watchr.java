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
		String upperCase = "";
		try {
			ipLog = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 	
		ipLog = "[" + ipLog + "]";
				
		String caller = getCallerTrace();
		
		try {
			upperCase = message.toUpperCase();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		
		s = " " + ipLog + "\t" + caller + "     "+ upperCase;

		return s;
	}

	private static String getCallerTrace() {

		String caller = "";
		String caller8 = "";
		String caller7 = "";
		String caller6 = "";
		String caller5 = "";
		String caller4 = "";

		try {
			caller8 = new Exception().getStackTrace()[8].getClassName();
		} catch (Exception e) {
		}
		
		try {
			caller7 = new Exception().getStackTrace()[7].getClassName();
		} catch (Exception e) {
		}
		
		try {
			caller6 = new Exception().getStackTrace()[6].getClassName();
		} catch (Exception e) {
		}

		try {
			caller5 = new Exception().getStackTrace()[5].getClassName();
		} catch (Exception e) {
		}
		
		try {
			caller4 = new Exception().getStackTrace()[4].getClassName();
		} catch (Exception e) {
		}
		

		if(!caller8.equals("")){
			caller = caller + caller8 + " -> ";
		}
		if(!caller7.equals("")){
			caller = caller + caller7 + " -> ";
		}
		if(!caller6.equals("")){
			caller = caller + caller6 + " -> ";
		}
		if(!caller5.equals("")){
			caller = caller + caller5 + " -> ";
		}
		if(!caller4.equals("")){
			caller = caller + caller4;
		}
		
		return caller = caller + " ==> ";
	}

	private static void fanoutLog(Level level, String message, String formattedMessage) {

		// TODO: add to CloudWatch Logs here

		// don't print individual chars
		if(message.length()<2) {
			return;
		}
		
		// print to console
		if(NetworkConfig.isMac()) {
			System.out.println(message);
		}
		
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
