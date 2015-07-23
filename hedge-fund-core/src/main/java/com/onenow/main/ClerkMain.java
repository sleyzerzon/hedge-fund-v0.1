package com.onenow.main;

import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.onenow.constant.StreamName;
import com.onenow.constant.StreamingData;
import com.onenow.data.EventActivity;
import com.onenow.data.EventActivityGenericStreaming;
import com.onenow.data.EventActivityGreekStreaming;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventActivityPriceStreaming;
import com.onenow.data.EventActivitySizeStreaming;
import com.onenow.data.EventActivityVolatilityStreaming;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.DBTimeSeriesGeneric;
import com.onenow.io.DBTimeSeriesGreek;
import com.onenow.io.DBTimeSeriesPrice;
import com.onenow.io.DBTimeSeriesSize;
import com.onenow.io.DBTimeSeriesVolatility;
import com.onenow.util.InitLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ClerkMain {

	/**
	 * Processes real-time data
	 * Data is generated by InvestorMain itself, and also by requests to it from HistorianRTMain
	 * @param args
	 */
	public static void main(String[] args) {

		InitLogger.run("");

		TimeParser.sleep(180); // boot sequence

		BusSystem.readAllStreams(InitialPositionInStream.LATEST);

	}
	
	public static void writeToL2(EventActivity event) {
	
		boolean success = false;
		boolean retry = false;
		
		int tries = 0;
		int maxTries = 3;
		
		while(!success) {
			// handle as a transaction, both price+size write or nothing
			try {
				tries++;
				success = true;
				if(	event instanceof EventActivityPriceHistory ||
					event instanceof EventActivityPriceSizeRealtime ||
					event instanceof EventActivityPriceStreaming) {
					
					DBTimeSeriesPrice.write(event);				
				}
				if( event instanceof EventActivityPriceSizeRealtime ||
					event instanceof EventActivitySizeStreaming) {
					
					DBTimeSeriesSize.write(event);
				}
				if( event instanceof EventActivityGreekStreaming ) {
					DBTimeSeriesGreek.write(event);
				}
				if ( event instanceof EventActivityVolatilityStreaming ) {
					DBTimeSeriesVolatility.write(event);
				}
				if ( event instanceof EventActivityGenericStreaming ) {
					DBTimeSeriesGeneric.write(event);
				}
			} catch (Exception e) {
				success = false;
				retry = true;
				Watchr.log(Level.SEVERE, "TSDB WRITE FAILED: " + event.toString() + " " + e.toString());	
				e.printStackTrace();
				if(tries>maxTries) {
					return;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
			}
		}
		if(retry) {
			Watchr.log(Level.WARNING, "> TSDB WRITE *RE-TRY* SUCCEEDED: " + event.timeInMilisec + " " + event.getInvestment().toString());
		}
	}
}
