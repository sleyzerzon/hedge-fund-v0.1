package com.onenow.test;

import java.util.logging.Level;

import com.onenow.util.Watchr;

public class ErrorLive {
	
	public ErrorLive() {
		
	}
	
	public void receiveError(String error) {
		
		String s = "ERRROR LIVE" + "\n";
		
		s = s + error;
		
		Watchr.log(Level.WARNING, s);
		
		// TODO: live error handling?
	}

}
