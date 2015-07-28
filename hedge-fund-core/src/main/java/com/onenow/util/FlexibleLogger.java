package com.onenow.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.Handler;

import com.onenow.admin.NetworkConfig;

public class FlexibleLogger {
	
  static public boolean debugMode = false;
  
  static private FileHandler fileTxt;
  static private SimpleFormatter formatterTxt;

  static private FileHandler fileHTML;
  static private Formatter formatterHTML;
  
  static public boolean setup() {
	  String mode = "";
	  return setup(Level.INFO, mode);
  }

  static public boolean setup(String mode) {

		if(!NetworkConfig.isMac()) {
			// redirect all System.out and System.err log
			
			if(debugMode) {
				redirectSystemStreams();
			}
		}

	  return setup(Level.INFO, mode);
  }

  
// CLOUDWATCH LOGS
  // everything is in root
// SSH to instance
// sudo su
// cd /root
// root@ip-172-31-36-250:~# sudo python ./awslogs-agent-setup.py --region us-east-1
//  ------------------------------------------------------
//  - Configuration file successfully saved at: /var/awslogs/etc/awslogs.conf
//  - You can begin accessing new log events after a few moments at https://console.aws.amazon.com/cloudwatch/home?region=us-east-1#logs:
//  - You can use 'sudo service awslogs start|stop|status|restart' to control the daemon.
//  - To see diagnostic information for the CloudWatch Logs Agent, see /var/log/awslogs.log
//  - You can rerun interactive setup using 'sudo python ./awslogs-agent-setup.py --region us-east-1 --only-generate-config'
//  ------------------------------------------------------

  
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
	    
	    String base = Watchr.getLogPath() + new Exception().getStackTrace()[3].getClassName()+appMode+"-Log.";
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
	    
	    // Start logging
	    System.out.println("Will log to: " + base + "*");
	    // System.getProperties().list(System.out);
	    Watchr.log("LOG PROPERTIES: " + System.getProperties().toString());
		Watchr.log(Level.INFO, "WHOLE ENVIRONMENT: " + RuntimeEnvironment.getEnv());

	  }
    catch (IOException e) {
    	success = false;
		Watchr.log(Level.SEVERE, e.getMessage());
        throw new RuntimeException("Problems with creating the log files");
      }
	  
	  return success;
  }
  
  /**
   * Redirects all System.out.println to Watchr
   * 
   */
  private static void redirectSystemStreams() {
	  
	  OutputStream outStream = new OutputStream() {

		@Override
	    public void write(final int b) throws IOException {
			updateLog(Level.INFO, String.valueOf((char) b));
	    }
	 
	    @Override
	    public void write(byte[] b, int off, int len) throws IOException {
	    	updateLog(Level.INFO, new String(b, off, len));
	    }
	 
	    @Override
	    public void write(byte[] b) throws IOException {
	      write(b, 0, b.length);
	    }
	  };
	 
	  OutputStream errStream = new OutputStream() {

		@Override
	    public void write(final int b) throws IOException {
			updateLog(Level.SEVERE, String.valueOf((char) b));
	    }
	 
	    @Override
	    public void write(byte[] b, int off, int len) throws IOException {
	    	updateLog(Level.SEVERE, new String(b, off, len));
	    }
	 
	    @Override
	    public void write(byte[] b) throws IOException {
	      write(b, 0, b.length);
	    }
	  };
	  
	  System.setOut(new PrintStream(outStream, true));
	  System.setErr(new PrintStream(errStream, true));
	}
  
 
  /** 
   * Thread the logger
   * @param text
   */
	  private static void updateLog(final Level level, final String text) {
		  
		  new Thread () {
			  @Override public void run () {
			    Watchr.log(level, text);
			  }
			}.start();
			
		}
}
 
