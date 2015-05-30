package com.onenow.main;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.onenow.data.DynamoDB;
import com.onenow.io.Kinesis;


public class BusMain {

	private static String streamName = "Bus";

	private static Region region = Region.getRegion(Regions.US_EAST_1); 
	
	public static void main(String[] args) throws Exception {
		
		Kinesis kinesis = new Kinesis(streamName, region);

		Object objToSend = (Object) "Hola World!";
		kinesis.sendPair(objToSend, streamName);
		
		DynamoDB dynamo = new DynamoDB(region);
		
	}
	
}
