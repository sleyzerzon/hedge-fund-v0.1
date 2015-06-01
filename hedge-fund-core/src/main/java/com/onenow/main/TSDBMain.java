package com.onenow.main;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.instrument.Investment;
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


//Long time = event.time; 
//Investment inv = event.inv; 
//TradeType tradeType = event.tradeType; 		
//InvDataSource source = event.source;
//InvDataTiming timing = event.timing;
//Double price = event.price;
//int size = event.size;
//writeRTtoL1(time, inv, tradeType, source, timing, price, size);		
