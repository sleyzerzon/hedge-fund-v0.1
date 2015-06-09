package com.onenow.io;

import java.util.HashMap;
import java.util.List;

import com.onenow.alpha.BrokerInterface;
import com.onenow.constant.BrokerMode;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.StreamName;
import com.onenow.constant.TradeType;
import com.onenow.data.EventRealTime;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

import java.util.logging.Level;

import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class CacheInProcess {
	
	private BrokerInterface 					broker;
	private HashMap<String, EventRealTime>		lastEventRT = new HashMap<String, EventRealTime>(); 	// last set of price/size/etc
	private HashMap<String, Chart>				charts = new HashMap<String, Chart>();			// price history in chart format from L1

	public CacheInProcess() {
		
	}
	
	public CacheInProcess(BrokerInterface broker) {
		this.broker = broker;
	}
	
	
	// REAL-TIME from broker
	public boolean writeEventRT(final EventRealTime event) {

		String key = Lookup.getInvestmentKey(	event.inv, event.tradeType,
												event.source, event.timing);
		
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
			lastEventRT.put(key, event);
			success = true;
		}
		
		// CRITICAL PATH
		new Thread () {
			@Override public void run () {
				writeEventThroughRing(event);
			}
		}.start();

		return success;
	}
		
	/** Upon writing every event to the ring, asynchronous update all charts in L0 from RTL1
	 * 
	 * @param event
	 */
	public void writeEventThroughRing(EventRealTime event) {

		// TODO: FAST WRITE TO RING		
		
		if(	broker.getMode().equals(BrokerMode.PRIMARY) ||
			broker.getMode().equals(BrokerMode.STANDBY)) {
						
			// Write to Real-Time datastream
			BusSystem.write(StreamName.REALTIME, event);			
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
			Watchr.log(Level.INFO, "Null price from cache");
			price = readPriceFromChart(inv, tradeType);
		} 
		
		Watchr.log(Level.INFO, "Cache PRICE READ: " + price);

		return price;
	}

	private Double readPriceFromL0(Investment inv, TradeType tradeType) {
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);
		Double price = lastEventRT.get(key).price;
		
		Watchr.log(Level.INFO, "Cache PRICE READ: L0 " + price);

		return price;
	}

	private Double readPriceFromChart(Investment inv, TradeType tradeType) {
		
		Double price;
		SamplingRate scalping = SamplingRate.TREND;
		String today = TimeParser.getTodayDashed();
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		Chart chart = readChart(	inv, tradeType, scalping, 
									today, today,
									source, timing);
		
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
		
		Watchr.log(Level.INFO, s + chart.toString());
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

		Watchr.log(Level.INFO, "Cache Chart READ: L0 " + chart.toString());
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
