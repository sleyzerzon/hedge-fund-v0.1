package com.onenow.main;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.util.FlexibleLogger;

public class CatchupMain {

	public static void main(String[] args) {
				
		FlexibleLogger.setup();

		IRecordProcessorFactory rtPrimaryProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
		BusSystem.read(StreamName.PRIMARY, rtPrimaryProcessorFactory);

		IRecordProcessorFactory rtStandbyProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
		BusSystem.read(StreamName.STANDBY, rtStandbyProcessorFactory);

		IRecordProcessorFactory rtRealtimeProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
		BusSystem.read(StreamName.REALTIME, rtRealtimeProcessorFactory);
	}

}
