package com.onenow.main;

import com.onenow.constant.StreamName;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.util.FlexibleLogger;

/**
 * Re-process the entire data stream (24hs) in kinesis
 * @author pablo
 *
 */
public class CatchupMain {

	public static void main(String[] args) {
				
		FlexibleLogger.setup();

		// InitialPositionInStream.TRIM_HORIZON
		BusSystem.read(StreamName.REALTIME, BusProcessingFactory.createProcessorFactoryEventRealTime());
	}

}
