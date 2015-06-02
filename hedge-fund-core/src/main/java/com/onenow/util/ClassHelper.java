package com.onenow.util;

public class ClassHelper {

	public ClassHelper() {
		
	}
	
	public static String getCallerName() {
		
		return new Exception().getStackTrace()[1].getClassName();
		
	}
	
	public static String getCaleeName() {
		
		return new Exception().getStackTrace()[0].getClassName();

	}
}
