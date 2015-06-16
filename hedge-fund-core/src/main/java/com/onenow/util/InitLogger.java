package com.onenow.util;

public class InitLogger {
	
	public InitLogger() {
		
	}

	public static void run(String stream) {
		SysProperties.setLogProperties();
		FlexibleLogger.setup(stream);		
	}
	public static void run() {
		run("");
	}
}
