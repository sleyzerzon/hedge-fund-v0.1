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
		String outFile = folderName+objectName+".txt";
		S3.object2File(bucket, objectName, outFile);
		
		TimeParser.wait(10);
		countWordsInFile(outFile);
		
		// countWordsInFile(args);

	}
	
	private static void countWordsInFile(String arg) {
		String args[] = {arg};
		countWordsInFile(args);
	}

	private static void countWordsInFile(String[] args) {
		
		WordCount counter = new WordCount();
		
		// load input data
		String inputFile = args[0];
		// String inputFile = "/users/Shared/HedgeFundLog.txt";
		JavaRDD<String> inputRDD = counter.loadInputData(inputFile);
		
		// split into words
		JavaRDD<String> wordsRDD = counter.splitIntoWords(inputRDD);
		
		// transform into pairs and count
		JavaPairRDD<String, Integer> countsRDD = counter.countWords(wordsRDD);
		
		// save the word count back out to a text file, causing evaluation
		String outputFile = args[1];
		// String outputFile = "";
		countsRDD.saveAsTextFile(outputFile);
	}
}
