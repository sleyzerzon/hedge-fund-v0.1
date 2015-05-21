package com.onenow.util;

public class WatchLog {

	public WatchLog() {
		
	}
	
	public void addToLog(LogType type, String caller, String message) {
		String s = "";
		String ip = ""; 	// TODO: get IP address
		s = ParseDate.getDashedNow() + " " + ip + " " + type + " " + " "+ caller + " "+ message;
		
		// TODO: add to CloudWatch Logs here
	}
}
