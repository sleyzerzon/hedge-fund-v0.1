package com.onenow.io;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.PriceType;
import com.onenow.data.EventActivity;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.Event;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestRaw;
import com.onenow.execution.BrokerInteractive;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

import java.util.logging.Level;

import com.onenow.util.RuntimeMetrics;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class CacheInProcess {
	
	private BrokerInteractive 							broker;
	private HashMap<String, EventActivityPriceSizeRealtime>		lastEventRT = new HashMap<String, EventActivityPriceSizeRealtime>(); 	// last set of price/size/etc
	private HashMap<String, Chart>						charts = new HashMap<String, Chart>();			// price history in chart format from L1

	public CacheInProcess() {
		
	}
	
	public CacheInProcess(BrokerInteractive broker) {
		this.broker = broker;
	}

	
/**
 * Call back from the broker to write events (Realtime, Streaming)
 * @param event
 * @return
 */
	public boolean writeEvent(final EventActivity event) {		
		boolean success = writeToMem(event);
		BusSystem.writeThreadActivityThroughRing(event);
		return success; 
	}

/**
 * Keep the last event of the kind in memory
 * @param event
 * @return
 */
	private boolean writeToMem(EventActivity event) {
		
		boolean success = false;	// useful to test for initialization
		
		Boolean writeToMem=false;
		// keep last in memory
		if(event instanceof EventActivityPriceSizeRealtime) {
			String key = Lookup.getPriceEventKey(event);
			success = writeRealtimeToMem(event, key, success, writeToMem);
			
			// TODO: fix calculation
			RuntimeMetrics.notifyWallstLatency((Long) (TimeParser.getTimeMilisecondsNow()-event.timeInMilisec), broker.getRole());
		}
		
		
		return success;
	}

	private boolean writeRealtimeToMem(EventActivity event, String key,
			boolean success, Boolean writeToMem) {
		if(lastEventRT.get(key) == null) { 	// never written before
			writeToMem = true;
		} else {
			if( event.timeInMilisec > lastEventRT.get(key).timeInMilisec ) {
				writeToMem = true;
			}
		}
		
		if(writeToMem) {
			Watchr.log(key + " " + event.toString());
			lastEventRT.put(key, (EventActivityPriceSizeRealtime) event);
			success = true;
		}
		return success;
	}
	
	
	// READ PRICE
	/**
	 * Gets the last price traded
	 * @param inv
	 * @param tradeType
	 * @return
	 */
	public double readPrice(Investment inv, PriceType tradeType) {

		// HIT
		Double price = readPriceFromL0(inv, tradeType);

		// MISS: fill with the last data from chart until RT events start to hit
		if(price==null) {
			Watchr.log(Level.INFO, "Null price from cache");
			price = readPriceFromChart(inv, tradeType);
		} 
		
		Watchr.log(Level.INFO, "Cache PRICE READ: " + price);

		return price;
	}

	private Double readPriceFromL0(Investment inv, PriceType tradeType) {
		
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
		
		
		Event event = new Event(inv, source, timing, tradeType);
		String key = Lookup.getPriceEventKey(event);
		EventActivityPriceSizeRealtime eventRT = lastEventRT.get(key); 
		Double price = eventRT.price;
		
		Watchr.log(Level.INFO, "Cache PRICE READ: L0 " + price);

		return price;
	}

	private Double readPriceFromChart(Investment inv, PriceType tradeType) {
		
		Double price;
		SamplingRate scalping = SamplingRate.TREND;
		String today = TimeParser.getTodayDashed();
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		EventRequest request = new EventRequestRaw(	DBQuery.MEAN, ColumnName.PRICE, 
													inv, tradeType, scalping, 
													today, today, 					// TODO: repeat today?
													source, timing);
		
		Chart chart = readChart(request);
		
		List<Candle> candles = chart.getPrices(); 
		
		Candle last = candles.get(candles.size()-1);
		price = last.closePrice;
		
		Watchr.log(Level.INFO, "Cache PRICE from Chart READ " + price);

		return price;
	
	}

	
	// READ CHARTS
	/**
	 * One-off chart read creates a chart from size and price information from the memory/database
	 * @param inv
	 * @param priceType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public Chart readChart(EventRequest request) {
		
		String s = "";

		// HIT? Memory is L0
		s = "Cache Chart HIT: L0";		
		Chart chart = readChartFromL0(request);
		
		// MISS: one-off requests, ok that they take longer for now
		if(chart==null) {
			s = "Cache Chart MISS: L0";
			
			// TODO: request Chartist to pre-fetch charts
//			chart = readChartToL0FromRTL1(	inv, tradeType, sampling, 
//												fromDate, toDate,
//												source, timing);
		} 
		
		Watchr.log(Level.INFO, s + chart.toString());
		return chart;
	}


	private Chart readChartFromL0(EventRequest request) {
		Chart chart = new Chart();
		String key = Lookup.getChartKey(request);
		chart = charts.get(key);

		Watchr.log(Level.INFO, "Cache Chart READ: L0 " + chart.toString());
		return chart;
	}

	
	// TODO: print all the data in memory, not just prices, from Maps/Ring/etc
	public String toString() {
		String s="";
		s = lastEventRT.toString();
		return s;
	}
	
	
	// SET GET

}
