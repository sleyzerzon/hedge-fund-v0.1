package com.onenow.main;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.EventHistory;
import com.onenow.io.Kinesis;
import com.onenow.util.FlexibleLogger;

public class ClerkHistoryMain {

	public static void main(String[] args) {

		FlexibleLogger.setup();

		Kinesis kinesis = BusSystem.getKinesis();
		
		StreamName streamName = StreamName.HISTORY;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.eventProcessorFactory();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
	}


	public static void writeHistoryToL2(EventHistory event) {

		
		
	}
	
}
	