package com.onenow.main;

import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.EventHistory;
import com.onenow.io.EventHistoryRT;
import com.onenow.io.Kinesis;
import com.onenow.io.TSDB;
import com.onenow.util.WatchLog;

public class TSDBWriteMain {

	public static void main(String[] args) {

		Kinesis kinesis = BusSystem.getKinesis();
		
		StreamName streamName = StreamName.REALTIME;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.recordProcessorEventRT();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
	}


	public static void writeRTtoL2(EventHistory event) {

		
	}
	
	public static void writeRTtoL2(EventHistoryRT event) {
	
		boolean success = false;
		boolean retry = false;
		
		while(!success) {
			// handle as a transaction, both price+size write or nothing
			try {
				success = true;
				TSDB.writePrice(event.time, event.inv, event.tradeType, event.price,
								event.source, event.timing);				
				TSDB.writeSize(	event.time, event.inv, event.tradeType, event.size,			
								event.source, event.timing);
			} catch (Exception e) {
				success = false;
				retry = true;
				String log = "TSDB RT TRANSACTION WRITE FAILED: " + event.time + " " + event.inv.toString();
				WatchLog.addToLog(Level.SEVERE, log);
	
				// e.printStackTrace();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
			}
		}
		if(retry) {
			String log = "> TSDB RT TRANSACTION WRITE *RE-TRY* SUCCEEDED: " + event.time + " " + event.inv.toString();
			WatchLog.addToLog(Level.INFO, log);
		}
	}

}
