package com.onenow.io;

import java.util.HashMap;
import java.util.List;

import com.onenow.alpha.Broker;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.DataSampling;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.WatchLog;

public class PriceSizeCache {
	
	private Broker 								broker;
	private HashMap<String, EventHistoryRT>			lastEventRT; 	// last set of price/size/etc
	private HashMap<String, Chart>				charts;			// price history in chart format from L1

	private TSDB 								TSDB = new TSDB();			// database	
	private DataSampling 							sampling = new DataSampling();
	private Lookup 								lookup = new Lookup();			// key
	private TimeParser							parseDate = new TimeParser();
	
	
	public PriceSizeCache() {
		
	}
	
	public PriceSizeCache(Broker broker) {
		this.broker = broker;
		this.lastEventRT = new HashMap<String, EventHistoryRT>();
		this.charts = new HashMap<String, Chart>();
	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME from broker
	public void writeEventRT(EventHistoryRT event) {

		boolean writeToMem = false;
		
		String key = lookup.getInvestmentKey(	event.inv, event.tradeType,
												event.source, event.timing);
		
		// keep last in memory
		if(lastEventRT.get(key) == null) { 	// never written before
			writeToMem = true;
		} else {		
			if( event.time > lastEventRT.get(key).time ) {
				writeToMem = true;
			}
		}
		
		if(writeToMem) {
			lastEventRT.put(key, event);
		}
			
		// CRITICAL PATH
		// TODO: FAST WRITE TO RING 
		writeEventThroughRing(event);
		
	}
		
	/** Upon writing every event to the ring, asynchronous update all charts in L0 from RTL1
	 * 
	 * @param event
	 */
	public void writeEventThroughRing(EventHistoryRT event) {

		// TODO: INSERT RING		
		
		if(	broker.getMode().equals(BrokerMode.PRIMARY) ||
			broker.getMode().equals(BrokerMode.STANDBY)) {
						
			// TODO: SQS/SNS ORCHESTRATION

			// Write to RT datastream
			BrokerBusHistorianRT rtBroker = new BrokerBusHistorianRT();
			rtBroker.write(event.toString());
			
			// TODO: move direct invocation to write RT to L1RT to back-end service
			Long time = event.time; 
			Investment inv = event.inv; 
			TradeType tradeType = event.tradeType; 		
			InvDataSource source = event.source;
			InvDataTiming timing = event.timing;
			Double price = event.price;
			int size = event.size;
			writeRTtoL1(time, inv, tradeType, source, timing, price, size);		

			// TODO: move update the charts to back-end service
			for(SamplingRate samplr:sampling.getList(SamplingRate.SCALP)) { // TODO: what sampling?
				
				System.out.println("\n" + "***** PRE-FETCH SAMPLING ***** " + samplr);
				// use miss function to force update of charts
				String today = parseDate.getDashedToday();
				readChartToL0FromRTL1(	inv, tradeType, samplr,
											parseDate.getDashedDateMinus(today, 1), today, // TODO: From/To Date actual
											source, timing);
				System.out.println("\n");
			}		
		}
	}

	private void writeRTtoL1(Long time, Investment inv, TradeType tradeType,
			InvDataSource source, InvDataTiming timing, Double price, int size) {
		
		boolean success = false;
		boolean retry = false;
		// TODO: handle as a transaction, both price+size write or nothing
		while(!success) {
			try {
				success = true;
				TSDB.writePrice(	time, inv, tradeType, price,
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

	
	// READ PRICE
	/**
	 * Gets the last price traded
	 * @param inv
	 * @param tradeType
	 * @return
	 */
	public double readPrice(Investment inv, TradeType tradeType) {

		// HIT
		Double price = readPriceFromL0(inv, tradeType);

		// MISS: fill with the last data from chart until RT events start to hit
		if(price==null) {
			System.out.println("Null price from cache");
			price = readPriceFromChart(inv, tradeType);
		} 
		
		System.out.println("Cache PRICE READ: " + price);

		return price;
	}

	private Double readPriceFromL0(Investment inv, TradeType tradeType) {
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		String key = lookup.getInvestmentKey(inv, tradeType, source, timing);
		Double price = lastEventRT.get(key).price;
		
		System.out.println("Cache PRICE READ: L0 " + price);

		return price;
	}

	private Double readPriceFromChart(Investment inv, TradeType tradeType) {
		
		Double price;
		SamplingRate scalping = SamplingRate.TREND;
		String today = parseDate.getDashedToday();
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		Chart chart = readChart(	inv, tradeType, scalping, 
									today, today,
									source, timing);
		
		List<Candle> candles = chart.getPrices(); 
		
		Candle last = candles.get(candles.size()-1);
		price = last.closePrice;
		
		System.out.println("Cache PRICE from Chart READ " + price);

		return price;
	
	}

	
	// READ CHARTS
	/**
	 * One-off chart read creates a chart from size and price information from the memory/database
	 * @param inv
	 * @param tradeType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public Chart readChart(	Investment inv, TradeType tradeType, SamplingRate sampling, 
							String fromDate, String toDate,
							InvDataSource source, InvDataTiming timing) {
		
		String s = "";

		// HIT? Memory is L0
		s = "Cache Chart HIT: L0";		
		Chart chart = readChartFromL0(	inv, tradeType, sampling, 
										fromDate, toDate, 
										source, timing);
		
		// MISS: one-off requests, ok that they take longer for now
		if(chart==null) {
			s = "Cache Chart MISS: L0";
			chart = readChartToL0FromRTL1(	inv, tradeType, sampling, 
												fromDate, toDate,
												source, timing);
		} 
		
		System.out.println(s + chart.toString());
		return chart;
	}


	private Chart readChartFromL0(	Investment inv, TradeType tradeType, SamplingRate sampling, 
									String fromDate, String toDate,
									InvDataSource source, InvDataTiming timing) {
		Chart chart = new Chart();
		String key = lookup.getChartKey(	inv, tradeType, sampling, 
												fromDate, toDate,
												source, timing);
		chart = charts.get(key);

		System.out.println("Cache Chart READ: L0 " + chart.toString());
		return chart;
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
	private Chart readChartToL0FromRTL1(	Investment inv, TradeType tradeType, SamplingRate samplingRate, 
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
			String key = lookup.getChartKey(	inv, tradeType, samplingRate, 
					fromDate, toDate,
					source, timing);

			charts.put(key, chart);				
						
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return chart;
	}

	
	// TEST
	
	// PRINT
	
	// TODO: print all the data in memory, not just prices, from Maps/Ring/etc
	public String toString() {
		String s="";
		s = lastEventRT.toString();
		return s;
	}
	
	
	// SET GET

}
