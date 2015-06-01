package com.onenow.io;

import com.onenow.constant.StreamName;


public class BrokerBusAnalyst {
	
	private static Integer numShards = 1;		

	public BrokerBusAnalyst() {
		
		Kinesis kinesis = BusSystem.getKinesis();
		
		kinesis.createStreamIfNotExists(StreamName.ANALYST, numShards); 

		String s = "WRITE ";
		
		BusSystem.write(kinesis, StreamName.ANALYST, s);

	}
	
}
