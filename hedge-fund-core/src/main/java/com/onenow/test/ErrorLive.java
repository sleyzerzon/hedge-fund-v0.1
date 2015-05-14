package com.onenow.test;

public class ErrorLive {
	
	public ErrorLive() {
		
	}
	
	public void receiveError(String error) {
		
		String s = "ERRROR LIVE" + "\n";
		
		s = s + error;
		
		System.out.println(s);
		
		// TODO: live error handling
	}

}
