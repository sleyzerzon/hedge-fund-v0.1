package com.onenow.main;

import com.onenow.util.InitLogger;
import com.onenow.util.RuntimeEnvironment;


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
		
		// 	VERSION=${env.CI_COMMIT_ID}
		// export JARS=/opt/hedge-fund/$VERSION/lib/
		// spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar

		
		RuntimeEnvironment.message("printenv > /tmp/env.txt");
		
		String message = "spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar";
		
		RuntimeEnvironment.message(message);
				
	}

}
