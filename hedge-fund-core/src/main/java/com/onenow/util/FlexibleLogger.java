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

  static public void setup(Level level) {

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
	    fileTxt = new FileHandler(WatchLog.getLogPath()+"HedgeFundLog.txt");
	    fileHTML = new FileHandler(WatchLog.getLogPath()+"HedgeFundLog.html");
	
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
        e.printStackTrace();
        throw new RuntimeException("Problems with creating the log files");
      }
  }
}
 
