package com.onenow.io;

import com.onenow.constant.StreamName;

public class BrokerBusHistorian {

	private static Kinesis kinesis = BusSystem.getKinesis();

	private static Integer numShards = 1;
	
	public BrokerBusHistorian() {
		kinesis.createStreamIfNotExists(StreamName.HISTORIAN, numShards); 
	}

	/** 
	 * Writes String
	 * @param s
	 */
	private void write(String s) {
		BusSystem.write(kinesis, StreamName.HISTORIAN, s);
	}

	/**
	 * Writes History object
	 * @param obj
	 */
	// TODO: use specific object type
	public void write(EventHistory obj) {
		BusSystem.write(kinesis, StreamName.HISTORIAN, obj);
	}
}
