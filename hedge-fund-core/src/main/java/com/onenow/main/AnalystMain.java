package com.onenow.main;

import java.util.logging.Level;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;

import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.alpha.WordCount;
import com.onenow.io.S3;
import com.onenow.util.InitLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class AnalystMain {
	
	public static void main(String[] args) throws Exception {

		InitLogger.run("");
		
		Watchr.log(Level.INFO, "checking");
		
		Bucket bucket = S3.getBucket(ReporterMain.getReporterBucketName());
		List<String> files = S3.listObjects(bucket);
		String objectName = files.get(0);
		
		String folderName = "/tmp/";
		String downloadedFile = folderName+objectName;
		S3.object2File(bucket, objectName, downloadedFile+".txt");
		
		countWordsInFile(downloadedFile+".txt", downloadedFile+".out");
		
		// countWordsInFile(args);

	}
	
	private static void countWordsInFile(String inputFile, String outputFolder) {
		String args[] = {inputFile, outputFolder};
		countWordsInFile(args);
	}

	// export SPARK_HOME=/Users/pablo/spark-1.3.1-bin-hadoop2.4
	// export PATH=$PATH:$SPARK_HOME/bin
	// export MAVEN_HOME=/Users/pablo/apache-maven-3.3.3
	// export PATH=$PATH:$MAVEN_HOME/bin
	// cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent
	// mvn -N clean install
	// mvn -Pdist -f hedge-fund-core/pom.xml clean package
	// export JARS=/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/
	// spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar
	private static void countWordsInFile(String[] args) {
				
		// load input data
		String inputFile = args[0];
		String outputFile = args[1];

		Watchr.info("Counting words from " + inputFile + " into " + outputFile);

		WordCount counter = new WordCount();

		// String inputFile = "/users/Shared/HedgeFundLog.txt";
		JavaRDD<String> inputRDD = counter.loadInputData(inputFile);
		
		// split into words
		JavaRDD<String> wordsRDD = counter.splitIntoWords(inputRDD);
		
		// transform into pairs and count
		JavaPairRDD<String, Integer> countsRDD = counter.countWords(wordsRDD);
		
		// save the word count back out to a text file, causing evaluation
		countsRDD.saveAsTextFile(outputFile);
	}
}
