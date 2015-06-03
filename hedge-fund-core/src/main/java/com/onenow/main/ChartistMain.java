package com.onenow.main;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.data.EventHistoryRT;
import com.onenow.instrument.Investment;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.Kinesis;
import com.onenow.io.Lookup;
import com.onenow.io.TSDB;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;

public class ChartistMain {

	private static HashMap<String, Chart>		charts = new HashMap<String, Chart>();			// price history in chart format from L1

	private static TSDB 						TSDB = new TSDB();								// database		
	
	/**
	 * Pre-fetches to L1 cache the chart analysis, based on the latest Real-Time data 
	 * @param args
	 */
	public static void main(String[] args) {

		FlexibleLogger.setup();

		Kinesis kinesis = BusSystem.getKinesis();
		
		StreamName streamName = StreamName.REALTIME;
		IRecordProcessorFactory recordProcessorFactory = BusProcessingFactory.eventProcessorFactory();

		BusSystem.read(kinesis, streamName, recordProcessorFactory);
		
		// TODO: look at chart L0 misses by the Investor?

	}
	
	public static void prefetchCharts(EventHistoryRT event) {
		
		for(SamplingRate samplr:DataSampling.getList(SamplingRate.SCALP)) { // TODO: what sampling?
			
			System.out.println("\n" + "***** PRE-FETCH SAMPLING ***** " + samplr);
			String today = TimeParser.getDashedToday();
			readChartToL1FromRTL2(	event.inv, event.tradeType, samplr,
									TimeParser.getDashedDateMinus(today, 1), today, // TODO: From/To Date actual
									event.source, event.timing);
			System.out.println("\n");
		}
	}
	
	/**
	 * Called periodically (and on L0 misses) to update charts in L0 from L1, 
	 * this method also progressively builds out the L1 from the L2
	 * @param inv
	 * @param tradeType
	 * @param samplingRate
	 * @param fromDate
	 * @param toDate
	 * @param source
	 * @param timing
	 * @return
	 */
	private static Chart readChartToL1FromRTL2(	Investment inv, TradeType tradeType, SamplingRate samplingRate, 
											String fromDate, String toDate,
											InvDataSource source, InvDataTiming timing) {		
		Chart chart = new Chart();
		
		try{	// some time series just don't exist or have data 			
			
			List<Candle> prices = TSDB.readPriceFromDB(		inv, tradeType, samplingRate, 
															fromDate, toDate,
															source, timing);
			List<Integer> sizes = TSDB.readSizeFromDB(		inv, tradeType, samplingRate, 
															fromDate, toDate,
															source, timing);

			chart.setPrices(prices);
			chart.setSizes(sizes);
			
			// keep last chart in L0 memory (with data)
			String key = Lookup.getChartKey(	inv, tradeType, samplingRate, 
					fromDate, toDate,
					source, timing);

			charts.put(key, chart);		
			
			// TODO: Write to ElastiCache
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return chart;
	}


}