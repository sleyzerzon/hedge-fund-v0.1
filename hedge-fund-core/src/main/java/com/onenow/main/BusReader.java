package com.onenow.main;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;

public class BusReader {

	private static String streamName = "BusXYZ";
	private static Integer numShards = 1;		
	private static Region region = Region.getRegion(Regions.US_EAST_1); 

	public static void main(String[] args) {
		
		Kinesis kinesis = BusSystem.getKinesis(streamName, region);
		
		// kinesis.createStream(streamName, numShards); 
		
		BusSystem.readFromBus(kinesis, streamName, region);

	}

}
