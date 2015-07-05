package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.admin.NetworkConfig;
import com.onenow.io.SQS;
import com.onenow.util.InitLogger;
import com.onenow.util.RuntimeEnvironment;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;


/** 
 * Runs Spark jobs fom a queue
 * @author pablo
 *
 */
// export SPARK_HOME=/Users/pablo/spark-1.3.1-bin-hadoop2.4
// export PATH=$PATH:$SPARK_HOME/bin
// export MAVEN_HOME=/Users/pablo/apache-maven-3.3.3
// export PATH=$PATH:$MAVEN_HOME/bin
// cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent
// mvn -N clean install
// mvn -Pdist -f hedge-fund-core/pom.xml clean package
// export JARS=/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/
// spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar
public class AnalystMain {
	
	public static void main(String[] args) throws Exception {

		InitLogger.run("");		
		
		runCommand(getWordCountCommand(args));

		
	  while(true) {
		  List<Message> serializedMessages = SQS.receiveMessages(SQS.getAnalystQueueURL());			  
		  if(serializedMessages.size()>0) {	
			  for(Message message: serializedMessages) {						
				  // TODO
			  } 
			  SQS.deleteMesssage(SQS.getHistoryQueueURL(), serializedMessages);
		  }
		  TimeParser.wait(1); // pace requests for messages from queue 
	  }		  
		  
	}

	private static void runCommand(String message) {
		try {
			if(!NetworkConfig.isMac()) {
				RuntimeEnvironment.execute(message);
			} else {
				buildTheJar();
				RuntimeEnvironment.execute(message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String buildTheJar() {
		String project = "/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/pom.xml";
		String mavenBin =  "/Users/pablo/apache-maven-3.3.3/bin/";
		String build = mavenBin + "mvn" + " " + "-Pdist -f" + " " + project + " " + "clean package";
		Watchr.log(Level.INFO, "To build, run this first: " + build);
		return build;
	}
	
	private static String getWordCountCommand(String[] args) {
		
		String theClass = "com.onenow.bigdata.WordCountMain";
		String jarPath = "";
		String jarName = "";
		String sparkBin = "";
		
		if(NetworkConfig.isMac()) {
			jarPath = "/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/";
			jarName = "hedge-fund-core-null.jar"; 
			sparkBin = "/Users/pablo/spark-1.3.1-bin-hadoop2.4/bin/";
		} else {
			String version = args[0];	// CI_COMMIT_ID
			jarPath = "/opt/hedge-fund/" + version + "/lib/";
			jarName = "hedge-fund-core-" + version + ".jar";
			sparkBin = "/opt/spark/bin/";
		}
			
		String command = 	sparkBin + "spark-submit" + " " +
							"--class" + " " + theClass + " " +
							jarPath+jarName;
			
		return command;
	}
	
	

}
