package com.onenow.alpha;

import java.util.Arrays;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import com.onenow.admin.InitSpark;

import scala.Tuple2;

public class WordCount {

	public static InitSpark spark = new InitSpark("local", "wordCount");
	
	public WordCount() {
	}
	
	public WordCount(String master) {
		spark = new InitSpark(master, "wordCount");
	}

	public static JavaRDD<String> loadInputData(String file) {
		return spark.sc.textFile(file);
	}

	public static JavaRDD<String> splitIntoWords(JavaRDD<String> inputRDD) {
		
		JavaRDD<String> wordsRDD = inputRDD.flatMap(
				new FlatMapFunction<String, String>() {
					@Override
					public Iterable<String> call(String x) throws Exception {
						return Arrays.asList(x.split(" "));
					}
				});
		return wordsRDD;
	}
	
	/** 
	 * Transform into pairs and count
	 * 
	 * @param wordsRDD
	 * @return
	 */
	public static JavaPairRDD<String, Integer> countWords(
			JavaRDD<String> wordsRDD) {
		
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
		return countsRDD;
	}
	
}
