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
import com.onenow.io.EventHistoryRT;
import com.onenow.io.Kinesis;
import com.onenow.io.TSDB;
import com.onenow.util.WatchLog;

public class TSDBMain {

	public static void main(String[] args) {

		Kinesis kinesis = BusSystem.getKinesis();
		
		// defaults to read interactive brokers
		StreamName streamName = StreamName.TSDB;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.recordProcessorEventRT();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
	}

	
public static void writeRTtoL2(EventHistoryRT event) {

	Long time = event.time; 
	Investment inv = event.inv; 
	TradeType tradeType = event.tradeType; 		
	InvDataSource source = event.source;
	InvDataTiming timing = event.timing;
	Double price = event.price;
	int size = event.size;
	
	writeRTtoL1(time, inv, tradeType, source, timing, price, size);		

}

public static void writeRTtoL1(Long time, Investment inv, TradeType tradeType,
		InvDataSource source, InvDataTiming timing, Double price, int size) {
	
	boolean success = false;
	boolean retry = false;
	// TODO: handle as a transaction, both price+size write or nothing
	while(!success) {
		try {
			success = true;
			TSDB.writePrice(time, inv, tradeType, price,
							source, timing);				
			TSDB.writeSize(	time, inv, tradeType, size,			
							source, timing);
		} catch (Exception e) {
			success = false;
			retry = true;
			String log = "TSDB RT TRANSACTION WRITE FAILED: " + time + " " + inv.toString();
			WatchLog.addToLog(Level.SEVERE, log);

			// e.printStackTrace();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {}
		}
	}
	if(retry) {
		String log = "> TSDB RT TRANSACTION WRITE *RE-TRY* SUCCEEDED: " + time + " " + inv.toString();
		WatchLog.addToLog(Level.INFO, log);
	}
}

}
