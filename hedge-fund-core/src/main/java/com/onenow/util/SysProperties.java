package com.onenow.util;

import java.util.Properties;

public class SysProperties {

	public SysProperties() {

	}
	
    // System properties
	// http://docs.oracle.com/javase/7/docs/api/java/util/logging/SimpleFormatter.html#format%28java.util.logging.LogRecord%29
	// the java.util.Formatter format string specified in the java.util.logging.SimpleFormatter.format property or the default format.  	
	//	    where the arguments are:
	//	    	1. date - a Date object representing event time of the log record.
	//	    	2. source - a string representing the caller, if available; otherwise, the logger's name.
	//	    	3. logger - the logger's name.
	//	    	4. level - the log level.
	//	    	5. message - the formatted log message returned from the Formatter.formatMessage(LogRecord) method. It uses java.text formatting and does not use the java.util.Formatter format argument.
	//	    	6. thrown - a string representing the throwable associated with the log record and its backtrace beginning with a newline character, if any; otherwise, an empty string.
	public static void setLogProperties() {
	    Properties properties = new Properties(System.getProperties());
	    properties.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tc] --%4$s-- %5$s %n");  // "%4$s: %5$s [%1$tc]%n"
	    System.setProperties(properties);
	    System.out.println("LOG FORMAT: " + System.getProperty("java.util.logging.SimpleFormatter.format"));	
	}
}
