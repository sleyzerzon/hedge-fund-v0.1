package com.onenow.alpha;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

public class InitSpark {

	public static JavaSparkContext sc;
	
	public InitSpark() {
		
	}
	
	public InitSpark(String master, String name) {
				
		// SparkConf conf = new SparkConf().setMaster(master).setAppName(name);
		// this.sc = new JavaSparkContext(conf);
		
		JavaSparkContext sc = new JavaSparkContext(	master, 
													name, 
													System.getenv("SPARK_HOME"), System.getenv("JARS"));
		
	}
}
