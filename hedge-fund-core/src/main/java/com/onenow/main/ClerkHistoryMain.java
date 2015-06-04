package com.onenow.main;

import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.StreamName;
import com.onenow.data.EventHistory;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;
import com.onenow.io.TSDB;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.WatchLog;

public class ClerkHistoryMain {

	/**
	 * Processes historical data gathered from 3rd party sources
	 * @param args
	 */
	public static void main(String[] args) {

		FlexibleLogger.setup();

		Kinesis kinesis = BusSystem.getKinesis();
		
		StreamName streamName = StreamName.HISTORY;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.processorFactoryEventHistoryRT();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
	}


	/**
	 * Write one row of quote history to TSDB, upon receiving it from a data stream
	 * @param event
	 */
	public static void writeHistoryToL2(EventHistory event) {

		// System.out.println("Cache History WRITE: L2 (from L3 via L0) "  + inv.toString() + " " + invHistory.toString());
		boolean success = false;
		boolean retry = false;
		while (!success) {
			try {
				success = true;
				TSDB.writePrice(	event.time*1000, event.investment, event.tradeType, event.open,
									event.source, event.timing);
			} catch (Exception e) {
				success = false;
				retry = true;
				String log = "TSDB HISTORY WRITE FAILED: " + event.investment.toString();
				WatchLog.addToLog(Level.SEVERE, log);
				// e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
			}				
		}
		if(retry) {
			String log = "> TSDB HISTORY WRITE *RE-TRY* SUCCESS: " + event.investment.toString();
			WatchLog.addToLog(Level.INFO, log);
		}
				
	}
	
}
	