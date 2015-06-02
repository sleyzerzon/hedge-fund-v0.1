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
import com.onenow.data.EventHistoryRT;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.WatchLog;

public class PriceSizeCache {
	
	private Broker 								broker;
	private HashMap<String, EventHistoryRT>		lastEventRT = new HashMap<String, EventHistoryRT>(); 	// last set of price/size/etc
	private HashMap<String, Chart>				charts = new HashMap<String, Chart>();			// price history in chart format from L1

	private TSDB 								TSDB = new TSDB();				// database		
	
	public PriceSizeCache() {
		
	}
	
	public PriceSizeCache(Broker broker) {
		this.broker = broker;
	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME from broker
	public void writeEventRT(EventHistoryRT event) {

		boolean writeToMem = false;
		
		String key = Lookup.getInvestmentKey(	event.inv, event.tradeType,
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
		
		
		// NOTE: ALSO PRE-FETCH CHARTS FROM L1 ELASTICACHE
	}
		
	/** Upon writing every event to the ring, asynchronous update all charts in L0 from RTL1
	 * 
	 * @param event
	 */
	public void writeEventThroughRing(EventHistoryRT event) {

		// TODO: INSERT RING		
		
		if(	broker.getMode().equals(BrokerMode.PRIMARY) ||
			broker.getMode().equals(BrokerMode.STANDBY)) {
						
			// TODO: SQS/SNS/ELASTICACHE ORCHESTRATION

			// Write to Real-Time datastream
			BrokerBusHistorianRT historyRTBroker = new BrokerBusHistorianRT();
			historyRTBroker.write(event);
			
			// moved to Chartist
//			writeRTtoL1(time, inv, tradeType, source, timing, price, size);		

			// moved to Chartist
			// prefetchCharts(inv, tradeType, source, timing);		
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
				
		String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);
		Double price = lastEventRT.get(key).price;
		
		System.out.println("Cache PRICE READ: L0 " + price);

		return price;
	}

	private Double readPriceFromChart(Investment inv, TradeType tradeType) {
		
		Double price;
		SamplingRate scalping = SamplingRate.TREND;
		String today = TimeParser.getDashedToday();
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
			
			// TODO: request Chartist to pre-fetch charts
//			chart = readChartToL0FromRTL1(	inv, tradeType, sampling, 
//												fromDate, toDate,
//												source, timing);
		} 
		
		System.out.println(s + chart.toString());
		return chart;
	}


	private Chart readChartFromL0(	Investment inv, TradeType tradeType, SamplingRate sampling, 
									String fromDate, String toDate,
									InvDataSource source, InvDataTiming timing) {
		Chart chart = new Chart();
		String key = Lookup.getChartKey(	inv, tradeType, sampling, 
												fromDate, toDate,
												source, timing);
		chart = charts.get(key);

		System.out.println("Cache Chart READ: L0 " + chart.toString());
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
