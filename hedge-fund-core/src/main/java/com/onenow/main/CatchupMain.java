package com.onenow.main;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.onenow.constant.StreamName;
import com.onenow.constant.StreamingData;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.SysProperties;

/**
 * Re-process the entire data stream (24hs) in kinesis
 * @author pablo
 *
 */
public class CatchupMain {

	public static void main(String[] args) {
				
		InitLogger.run("");
		
		// HISTORY
		BusSystem.read(	BusSystem.getStreamName(StreamingData.PRICE_HISTORY), 
				BusProcessingFactory.createProcessorFactoryEventPriceSizeRealtime(BusSystem.getStreamName(StreamingData.PRICE_HISTORY)),
				InitialPositionInStream.TRIM_HORIZON);

		BusSystem.read(	BusSystem.getStreamName(StreamingData.GREEK_HISTORY), 
				BusProcessingFactory.createProcessorFactoryEventPriceSizeRealtime(BusSystem.getStreamName(StreamingData.GREEK_HISTORY)),
				InitialPositionInStream.TRIM_HORIZON);

		
		// REALTIME
		BusSystem.read(	BusSystem.getStreamName(StreamingData.PRICESIZE_REALTIME), 
						BusProcessingFactory.createProcessorFactoryEventPriceSizeRealtime(BusSystem.getStreamName(StreamingData.PRICESIZE_REALTIME)),
						InitialPositionInStream.TRIM_HORIZON);

		// STREAMING
		BusSystem.read(	BusSystem.getStreamName(StreamingData.PRICE_STREAMING), 
						BusProcessingFactory.createProcessorFactoryEventPriceStreaming(BusSystem.getStreamName(StreamingData.PRICE_STREAMING)),
						InitialPositionInStream.TRIM_HORIZON);

		BusSystem.read(	BusSystem.getStreamName(StreamingData.SIZE_STREAMING), 
						BusProcessingFactory.createProcessorFactoryEventSizeStreaming(BusSystem.getStreamName(StreamingData.SIZE_STREAMING)),
						InitialPositionInStream.TRIM_HORIZON);

		BusSystem.read(	BusSystem.getStreamName(StreamingData.GREEK_STREAMING), 
						BusProcessingFactory.createProcessorFactoryEventGreekStreaming(BusSystem.getStreamName(StreamingData.GREEK_STREAMING)),
						InitialPositionInStream.TRIM_HORIZON);

		BusSystem.read(	BusSystem.getStreamName(StreamingData.VOLATILITY_STREAMING), 
						BusProcessingFactory.createProcessorFactoryEventVolatilityStreaming(BusSystem.getStreamName(StreamingData.VOLATILITY_STREAMING)),
						InitialPositionInStream.TRIM_HORIZON);

		BusSystem.read(	BusSystem.getStreamName(StreamingData.GENERIC_STREAMING), 
						BusProcessingFactory.createProcessorFactoryEventGenericStreaming(BusSystem.getStreamName(StreamingData.GENERIC_STREAMING)),
						InitialPositionInStream.TRIM_HORIZON);

	}

}
