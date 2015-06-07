package com.onenow.io;

import org.testng.annotations.Test;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.constant.TradeType;
import com.onenow.data.EventRealTime;
import com.onenow.instrument.Investment;
import com.onenow.util.TimeParser;

public class KinesisTest {

	static IRecordProcessorFactory testingProcessorFactory = BusProcessingFactory.processorFactoryString();

	private Long time = new Long("1424288913903");
	private Investment inv = new Investment();
	private TradeType tradeType = TradeType.TRADED; 

	private Double price = 2011.0;
	private Integer size = 5;
	
	private InvDataSource source = InvDataSource.IB;
	private InvDataTiming timing = InvDataTiming.REALTIME;

	/**
	 * Write repeatedly to a data stream.  Have record processor write to cache.  Then read and validate it write the right amount.
	 */
	public static void run() {	
		new Thread () {
			@Override public void run () {
				writeRepeatedly();
			}
		}.start();
		readRepeatedly(); 
	}
	  private static void writeRepeatedly() {
		  	int count = 0;
		  	while(true) {	
				BusSystem.write(StreamName.TESTING, TestValues.CACHEVALUE.toString());
				TimeParser.wait(5);
				count ++;
				if(count>10) {
					return;
				}
		  	}
	  } 
	  private static void readRepeatedly() {
			BusSystem.read(StreamName.TESTING, testingProcessorFactory);
	  }

}
