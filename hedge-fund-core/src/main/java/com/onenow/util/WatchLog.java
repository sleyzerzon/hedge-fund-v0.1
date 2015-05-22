package com.onenow.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class WatchLog {

	public WatchLog() {
		
	}

	public static String addToLog(LogType type, String message, String prepend, String postpend) {

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
		
		return s;
				
	}
	
	public static String addToLog(LogType type, String message) {

		return addToLog(type, message, "", "");

	}
}
