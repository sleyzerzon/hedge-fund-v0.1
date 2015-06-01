package com.onenow.main;

import com.onenow.constant.StreamName;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;

public class HistorianRTBroker {

	private static Integer numShards = 1;
	
	public static void main(String[] args) {
				
		Kinesis kinesis = BusSystem.getKinesis();
		
		kinesis.createStreamIfNotExists(StreamName.HISTORIANRT, numShards); 

		String s = "WRITE ";
		
		BusSystem.write(kinesis, StreamName.HISTORIANRT, s);

	}

}
