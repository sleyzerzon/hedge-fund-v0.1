package com.onenow.io;

import com.onenow.constant.StreamName;

public class BrokerBusHistorianRT {

	static Kinesis kinesis = BusSystem.getKinesis();

	private static Integer numShards = 1;
	
	public BrokerBusHistorianRT() {
		kinesis.createStreamIfNotExists(StreamName.REALTIME, numShards); 
	}

	/** Writes string
	 * 
	 * @param s
	 */
	static public void write(String s) {
		BusSystem.write(kinesis, StreamName.REALTIME, s);		
	}
	
	/**
	 * Writes Real Time object
	 * @param event
	 */
	static public void write(EventHistoryRT event) {
		BusSystem.write(kinesis, StreamName.REALTIME, event);		
	}

}
