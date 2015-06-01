package com.onenow.main;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.constant.StreamName;
import com.onenow.data.DynamoDB;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;


public class AnalystBroker {
	
	private static Integer numShards = 1;		

	public static void main(String[] args) throws Exception {
	
		Kinesis kinesis = BusSystem.getKinesis();
		
		kinesis.createStreamIfNotExists(StreamName.ANALYST, numShards); 

		String s = "WRITE ";
		
		BusSystem.write(kinesis, StreamName.ANALYST, s);

	}
	
}
