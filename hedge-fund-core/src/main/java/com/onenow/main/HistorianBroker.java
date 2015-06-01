package com.onenow.main;

import com.onenow.constant.StreamName;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;

public class HistorianBroker {

	private static Integer numShards = 1;
	
	public static void main(String[] args) {

		Kinesis kinesis = BusSystem.getKinesis();
		
		kinesis.createStreamIfNotExists(StreamName.HISTORIAN, numShards); 

		String s = "WRITE ";
		
		BusSystem.write(kinesis, StreamName.HISTORIAN, s);

	}

}
