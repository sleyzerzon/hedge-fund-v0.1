package com.onenow.alpha;

import java.util.Arrays;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkWordCount {


	public static void main(String[] args) throws Exception {

		String master = args[0];
		InitSpark spark = new InitSpark(master, "wordCount");

		String inputFile = null;
		
		// load input data
		JavaRDD<String> inputRDD = spark.sc.textFile(args[1]);
		
		// split into words
		JavaRDD<String> wordsRDD = inputRDD.flatMap(	
				new FlatMapFunction<String, String>() {
					@Override
					public Iterable<String> call(String x) throws Exception {
						return Arrays.asList(x.split(" "));
					}
				});
		
		// transform into pairs and count
		JavaPairRDD<String, Integer> countsRDD = wordsRDD.mapToPair(	
				new PairFunction<String, String, Integer>() {
					@Override
					public Tuple2<String, Integer> call(String t) throws Exception {
						return new Tuple2(t, 1);
					}
				}).reduceByKey(
						new Function2<Integer, Integer, Integer>() {
							public Integer call(Integer x, Integer y) {
								return x+y;
							}
				});
		
		String outputFile = args[2];
		// save teh word count back out to a text file, causing evaluation
		countsRDD.saveAsTextFile(outputFile);
	}
}
