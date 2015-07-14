package com.onenow.main;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.IRecordProcessorFactory;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.InitialPositionInStream;
import com.onenow.admin.NetworkConfig;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.TestValues;
import com.onenow.constant.PriceType;
import com.onenow.data.DataSampling;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestRaw;
import com.onenow.data.EventRequestRealtime;
import com.onenow.instrument.Investment;
import com.onenow.io.BusProcessingFactory;
import com.onenow.io.BusSystem;
import com.onenow.io.CacheElastic;
import com.onenow.io.DBTimeSeriesPrice;
import com.onenow.io.DBTimeSeriesSize;
import com.onenow.io.Kinesis;
import com.onenow.io.Lookup;
import com.onenow.io.DBTimeSeries;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.Piping;
import com.onenow.util.SysProperties;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ChartistMain {

	private static HashMap<String, Chart>		charts = new HashMap<String, Chart>();			// price history in chart format from L1

	
	private static HashMap<SamplingRate, Long> lastQueryStamp = new HashMap<SamplingRate, Long>();
	
	/**
	 * Pre-fetches to L1 cache the chart analysis, based on the latest Real-Time data 
	 * @param args
	 */
	public static void main(String[] args) {

		InitLogger.run("");

		testCache();
		
		StreamName streamName = BusSystem.getStreamName("PRIMARY");

		BusSystem.read(	streamName, 
						BusProcessingFactory.createProcessorFactoryEventRealTime(streamName),
						InitialPositionInStream.LATEST);

	}
	
	private static void testCache() {
		
		if(!NetworkConfig.isMac()) {
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
	}
	
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	public static void prefetchCharts(EventActivityPriceSizeRealtime event) {		
			
		for(SamplingRate samplr:DataSampling.getList(SamplingRate.SCALP)) { // TODO: what sampling?

			prefetchPacedCharts(event, samplr);
		}		
	}

	private static void prefetchPacedCharts(EventActivityPriceSizeRealtime event, SamplingRate samplingRate) {
		
		Long lastStamp = getLastStamp(samplingRate);
		Long elapsedTime = TimeParser.getTimestampNow()-lastStamp;
		Watchr.log(Level.WARNING, "ELAPSED " + elapsedTime + " FOR " + samplingRate);

		if( elapsedTime > 10000 ) {

			Watchr.log(Level.INFO, "@@@@@@@@@@ PRE-FETCH SAMPLING: " + samplingRate, "\n", "");

			String toDashedDate = TimeParser.getTodayDashed();
			String fromDate = TimeParser.getDateMinusDashed(toDashedDate, 1);		

			EventRequestRealtime request = new EventRequestRealtime(event.getInvestment(), event.source, event.timing, 
																	samplingRate, event.priceType, 
																	fromDate, toDashedDate);
			
			// TODO convert Request type
			// readChartToL1FromRTL2(request);
			
			lastQueryStamp.put(samplingRate, TimeParser.getTimestampNow()); 

		}
	}

	private static Long getLastStamp(SamplingRate samplr) {
		Long lastStamp = lastQueryStamp.get(samplr);
		if(lastStamp==null) {
			lastStamp = (long) 0;
		}
		return lastStamp;
	}
	
	/**
	 * Called periodically (and on L0 misses) to update charts in L0 from L1, 
	 * this method also progressively builds out the L1 from the L2
	 * @param inv
	 * @param priceType
	 * @param samplingRate
	 * @param fromDate
	 * @param toDate
	 * @param source
	 * @param timing
	 * @return
	 */
	private static Chart readChartToL1FromRTL2(EventRequestRaw request) {		
		Chart chart = new Chart();
		
		try{	// some time series just don't exist or have data 			
			
			List<Candle> prices = DBTimeSeriesPrice.read(request);
			List<Integer> sizes = DBTimeSeriesSize.read(request);

			chart.setPrices(prices);
			chart.setSizes(sizes);
			
			// keep last chart in L0 memory (with data)
			String key = Lookup.getChartKey(request);

			// store in process memory
			charts.put(key, chart);			
			
			// Write to ElastiCache
			// CacheElastic.write(key, (Object) chart); 
			// CacheElastic.read(key);

			CacheElastic.write(key,  Piping.serialize((Object) chart));
			Object readObject = CacheElastic.read(key);
			Watchr.log(Level.INFO, "Read from elastic cache: " + readObject);
						
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.getMessage());
		}
		
		return chart;
	}


}
