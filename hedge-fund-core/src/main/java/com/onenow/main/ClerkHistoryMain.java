package com.onenow.main;

import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.onenow.constant.StreamName;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.DBTimeSeriesPrice;
import com.onenow.io.Kinesis;
import com.onenow.io.DBTimeSeries;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.SysProperties;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ClerkHistoryMain {

	/**
	 * Processes historical data gathered from 3rd party sources
	 * @param args
	 */
	public static void main(String[] args) {

		InitLogger.run("");

		TimeParser.sleep(180); // boot sequence

		StreamName streamName = BusSystem.getStreamName("HISTORY");

		BusSystem.read(	streamName, 
						BusProcessingFactory.createProcessorFactoryEventHistory(streamName),
						InitialPositionInStream.LATEST);
		
	}


	/**
	 * Write one row of quote history to TSDB, upon receiving it from a data stream
	 * @param event
	 */
	public static void writeHistoryPriceToL2(EventActivityPriceHistory event) {

		// System.out.println("Cache History WRITE: L2 (from L3 via L0) "  + inv.toString() + " " + invHistory.toString());
		boolean success = false;
		boolean retry = false;
		while (!success) {
			try {
				success = true;
				DBTimeSeriesPrice.write(event);
			} catch (Exception e) {
				success = false;
				retry = true;
				// e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
			}				
		}
		if(retry) {
			Watchr.log(Level.INFO, "> TSDB HISTORY WRITE *RE-TRY* SUCCESS: " + event.getInvestment().toString());
		}
				
	}
	
}
	