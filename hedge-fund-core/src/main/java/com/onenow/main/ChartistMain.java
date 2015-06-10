package com.onenow.main;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.data.EventRealTime;
import com.onenow.instrument.Investment;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.CacheElastic;
import com.onenow.io.Kinesis;
import com.onenow.io.Lookup;
import com.onenow.io.databaseTimeSeries;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ChartistMain {

	private static HashMap<String, Chart>		charts = new HashMap<String, Chart>();			// price history in chart format from L1

	
	/**
	 * Pre-fetches to L1 cache the chart analysis, based on the latest Real-Time data 
	 * @param args
	 */
	public static void main(String[] args) {

		FlexibleLogger.setup();

		testCache();
		
		new Thread () {
			@Override public void run () {
				IRecordProcessorFactory rtPrimaryProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
				BusSystem.read(StreamName.PRIMARY, rtPrimaryProcessorFactory);
			}
		}.start();

		new Thread () {
			@Override public void run () {
				IRecordProcessorFactory rtStandbyProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
				BusSystem.read(StreamName.STANDBY, rtStandbyProcessorFactory);
			}
		}.start();

		new Thread () {
			@Override public void run () {
				IRecordProcessorFactory rtRealtimeProcessorFactory = BusProcessingFactory.processorFactoryEventRealTime();
				BusSystem.read(StreamName.REALTIME, rtRealtimeProcessorFactory);
			}
		}.start();

	}
	
	private static void testCache() {
		
		  CacheElastic.write(TestValues.KEY.toString(), (Object) TestValues.VALUE.toString());
		  TimeParser.wait(5);
		  String testValue = (String) CacheElastic.readAsync(TestValues.KEY.toString());
		  TimeParser.wait(5);

		  try {
			if(testValue.equals(TestValues.VALUE.toString())) {
				  Watchr.log(Level.WARNING, "ElastiCache test PASS");
			  } 
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, "ElastiCache test FAILURE");
			e.printStackTrace();
		}		    
	}
	
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	public static void prefetchCharts(EventRealTime event) {
		
		for(SamplingRate samplr:DataSampling.getList(SamplingRate.SCALP)) { // TODO: what sampling?
			
	    	Watchr.log(Level.INFO, "@@@@@@@@@@ PRE-FETCH SAMPLING: " + samplr, "\n", "");

			String today = TimeParser.getTodayDashed();
			readChartToL1FromRTL2(	event.inv, event.tradeType, samplr,
									TimeParser.getDateMinusDashed(today, 1), today, // TODO: From/To Date actual
									event.source, event.timing);
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
			
			List<Candle> prices = databaseTimeSeries.readPriceFromDB(	inv, tradeType, samplingRate, 
																		fromDate, toDate,
																		source, timing);
			List<Integer> sizes = databaseTimeSeries.readSizeFromDB(	inv, tradeType, samplingRate, 
																		fromDate, toDate,
																		source, timing);

			chart.setPrices(prices);
			chart.setSizes(sizes);
			
			// keep last chart in L0 memory (with data)
			String key = Lookup.getChartKey(	inv, tradeType, samplingRate, 
												fromDate, toDate,
												source, timing);

			// store in process memory
			charts.put(key, chart);			
			// Write to ElastiCache
			CacheElastic.write(key, (Object) chart);
				
			CacheElastic.read(key);

						
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.getMessage());
		}
		
		return chart;
	}


}
