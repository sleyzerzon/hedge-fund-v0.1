package com.onenow.io;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityRealtime;
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
	private HashMap<String, EventActivityRealtime>		lastEventRT = new HashMap<String, EventActivityRealtime>(); 	// last set of price/size/etc
	private HashMap<String, Chart>						charts = new HashMap<String, Chart>();			// price history in chart format from L1

	public CacheInProcess() {
		
	}
	
	public CacheInProcess(BrokerInteractive broker) {
		this.broker = broker;
	}

	
	// REAL-TIME from broker
	public boolean writeEventRT(final EventActivityRealtime event) {

		String key = Lookup.getEventKey(event);
		
		boolean success = false;
		
		Boolean writeToMem=false;
		// keep last in memory
		if(lastEventRT.get(key) == null) { 	// never written before
			writeToMem = true;
		} else {
			if( event.time > lastEventRT.get(key).time ) {
				writeToMem = true;
			}
		}
		
		if(writeToMem) {
			Watchr.log(key + " " + event.toString());
			lastEventRT.put(key, event);
			success = true;
		}
		
		// CRITICAL PATH
		
		new Thread () {
			@Override public void run () {
				writeEventThroughRing(event);
			}
		}.start();

			
		RuntimeMetrics.notifyWallstLatency((Long) TimeParser.getTimestampNow()/1000-event.time, broker.getStream());
		
		return success;
	}
		
	/** Upon writing every event to the ring, asynchronous update all charts in L0 from RTL1
	 * 
	 * @param event
	 */
	public void writeEventThroughRing(EventActivityRealtime event) {

		// TODO: FAST WRITE TO RING		

		// Write to Real-Time datastream
		StreamName stream = broker.getStream();
		Watchr.log(Level.WARNING, "Writing into Stream " + "<" + stream + ">" + " OBJECT: " + event.toString());
		BusSystem.write(event, stream);
						
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
			Watchr.log(Level.INFO, "Null price from cache");
			price = readPriceFromChart(inv, tradeType);
		} 
		
		Watchr.log(Level.INFO, "Cache PRICE READ: " + price);

		return price;
	}

	private Double readPriceFromL0(Investment inv, TradeType tradeType) {
		
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
		
		
		Event event = new Event(inv, source, timing, tradeType);
		String key = Lookup.getEventKey(event);
		EventActivityRealtime eventRT = lastEventRT.get(key); 
		Double price = eventRT.price;
		
		Watchr.log(Level.INFO, "Cache PRICE READ: L0 " + price);

		return price;
	}

	private Double readPriceFromChart(Investment inv, TradeType tradeType) {
		
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
	 * @param tradeType
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
