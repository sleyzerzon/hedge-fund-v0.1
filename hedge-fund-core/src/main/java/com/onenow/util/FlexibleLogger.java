package com.onenow.util;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

public class FlexibleLogger {
  static private FileHandler fileTxt;
  static private SimpleFormatter formatterTxt;

  static private FileHandler fileHTML;
  static private Formatter formatterHTML;

  static private String logMode1=null;
  static private String logMode2=null;
  
  static public boolean setup() {
	  String mode = "";
	  return setup(Level.INFO, mode);
  }

  static public boolean setup(String mode) {
	  logMode1 = mode;
	  return setup(Level.INFO, logMode1);
  }

  static public boolean setup(String mode1, String mode2) {
	  logMode1 = mode1;
	  logMode2 = mode2;
	  setup(Level.INFO, logMode1);
	  setup(Level.INFO, logMode2);
	  return true;
  }

  static public boolean setup(Level level, String appMode) {
	  
	  boolean success = true;
	  
	  try {
	    // get the global logger to configure it
	    Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	    // suppress the logging output to the console
	    Logger rootLogger = Logger.getLogger("");
	    Handler[] handlers = rootLogger.getHandlers();
	    if (handlers[0] instanceof ConsoleHandler) {
	      rootLogger.removeHandler(handlers[0]);
	    }
	
	    // default to info
		// set the LogLevel to Info, severe, warning and info will be written
	    // set the LogLevel to Severe, only severe Messages will be written
	    logger.setLevel(level);
	    
	    String base = Watchr.getLogPath() + new Exception().getStackTrace()[2].getClassName()+appMode+"Log.";
	    System.out.println("Will log to: " + base + "*");
	    fileTxt = new FileHandler(base+"txt");
	    fileHTML = new FileHandler(base+"html");
	
	    // create a TXT formatter
	    formatterTxt = new SimpleFormatter();
	    fileTxt.setFormatter(formatterTxt);
	    logger.addHandler(fileTxt);
	
	    // create an HTML formatter
	    formatterHTML = new HtmlFormatter();
	    fileHTML.setFormatter(formatterHTML);
	    logger.addHandler(fileHTML);    
	  }
    catch (IOException e) {
    	success = false;
        e.printStackTrace();
        throw new RuntimeException("Problems with creating the log files");
      }
	  
	  return success;
  }
}
 
