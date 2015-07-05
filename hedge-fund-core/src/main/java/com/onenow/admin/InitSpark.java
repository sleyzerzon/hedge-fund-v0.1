package com.onenow.admin;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class InitSpark {

		// use stop() to shut down
		public static JavaSparkContext sc;
		
		public InitSpark() {
		}
		
		// use master to connect to a cluster
		public InitSpark(String master, String appName) {
			setContext(master, appName);		
		}

		private void setContext(String master, String appName) {
			try {
//				SparkConf conf = new SparkConf().setMaster(master).setAppName(appName);
//				 InitSpark.sc = new JavaSparkContext(conf);
				 
				this.sc = new JavaSparkContext(	master, appName, 
												System.getenv("SPARK_HOME"), System.getenv("JARS"));
						 
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
}
