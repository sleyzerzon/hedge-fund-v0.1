package com.onenow.main;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.onenow.data.DynamoDB;
import com.onenow.data.DynamoDBCountPersister;
import com.onenow.io.Kinesis;


public class BusMain {

	private static String streamName = "Bus";
	private static Integer numShards = 2;	
	private static Region region = Region.getRegion(Regions.US_EAST_1); 
	
	private static Kinesis kinesis = new Kinesis(streamName, region);	
	private static DynamoDB dynamo = new DynamoDB(region);

	
	public static void main(String[] args) throws Exception {

		writeToBus();
		
		readFromBus();
				
	}
	
	private static boolean writeToBus() {			
		kinesis.createStream(streamName, numShards);
		Object objToSend = (Object) "Hola World!";
		kinesis.sendPair(objToSend, streamName);
		
		return true;
	}
	
	private static boolean readFromBus() {

		String applicationName = "appName";
		String workerId = "fulano";
		KinesisClientLibConfiguration clientConfig = kinesis.configureClient(applicationName, streamName, workerId, region);
		
		String tableName = "tableName";
		
		DynamoDBCountPersister persister = dynamo.getCountPersister(tableName);
		
        Worker kinesysWorker = new Worker(kinesis.dynamoRecordProcessor(tableName, persister), clientConfig);

        try {
            kinesysWorker.run();
        } catch (Throwable t) {
        	System.out.println("Caught throwable while processing data." + t);
            return false;
        }
        
		return true;
	}
}
