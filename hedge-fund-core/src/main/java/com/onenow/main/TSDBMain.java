package com.onenow.main;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;

public class TSDBMain {

	public static void main(String[] args) {

		Kinesis kinesis = BusSystem.getKinesis();
		
		// defaults to read interactive brokers
		StreamName streamName = StreamName.TSDB;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.ibRecordProcessor();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
	}

}
