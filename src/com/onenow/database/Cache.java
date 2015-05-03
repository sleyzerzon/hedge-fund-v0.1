package com.onenow.database;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Sampling;
import com.onenow.execution.Broker;
import com.onenow.execution.QuoteHistory;
import com.onenow.execution.QuoteRow;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.ParseDate;

public class Cache {
	
	private Broker 	broker;
	private TSDB 	TSDB;			// database
	
	private Sampling 	sampling;
	private Lookup 	lookup;			// key
	
	private HashMap<String, EventRT>	lastEventRT; 	// last set of price/size/etc
	private HashMap<String, Chart>		charts;			// price history in chart format

	private ParseDate	parseDate = new ParseDate();

	

	public Cache() {
		
	}
	
	public Cache(Broker broker) {
		this.broker = broker;
		this.lookup = new Lookup();
		this.lastEventRT = new HashMap<String, EventRT>();
		this.charts = new HashMap<String, Chart>();
		this.TSDB = new TSDB();
		this.sampling = new Sampling();
	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME from broker
	public void writeEventRT(EventRT event) {

		boolean writeToMem = false;
		
		String key = lookup.getInvestmentKey(	event.getInv(), event.tradeType,
												event.getSource(), event.getTiming());
		
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
		
		// CRITICAL PATH: fast write to ring
		writeEventToRing(event);
	}
		
	public void writeEventToRing(EventRT event) {

		Long time = event.time; 
		Investment inv = event.getInv(); 
		TradeType tradeType = event.tradeType; 
		
		InvDataSource source = event.getSource();
		InvDataTiming timing = event.getTiming();

		Double price = event.price;
		int size = event.getSize();

		// TODO: INSERT RING
		// write
		TSDB.writePrice(	time, inv, tradeType, price,
								source, timing);				
		TSDB.writeSize(	time, inv, tradeType, size,			
								source, timing);		
		
		// TODO: SQS/SNS ORCHESTRATION
		
		// update the charts
		for(SamplingRate sampling:sampling.getList(SamplingRate.SCALPSHORT)) { // TODO: what sampling?
			// use miss function to force update of charts
			readthroughChartFromL12(	inv, tradeType, sampling,
										parseDate.getYesterday(), parseDate.getTomorrow(), // TODO: From/To Date actual
										source, timing);
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
		SamplingRate scalping = SamplingRate.SCALP;
		String today = getParser().getToday();
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
				
		Chart chart = readChart(	inv, tradeType, scalping, 
									today, today,
									source, timing);
		
		List<Candle> candles = chart.getPrices(); 
		
		Candle last = candles.get(candles.size()-1);
		price = last.getClosePrice();
		
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
			chart = readthroughChartFromL12(	inv, tradeType, sampling, 
												fromDate, toDate,
												source, timing);

		} 
		
		System.out.println(s + "\n" + chart.toString());

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

		System.out.println("Cache Chart READ: L0" + "\n" + chart.toString());
		return chart;
	}


	/**
	 * Called periodically to update charts from L1, and on L0 misses, 
	 * this method also progressively builds out the L1 from the L2
	 * @param inv
	 * @param tradeType
	 * @param sampling
	 * @param fromDate
	 * @param toDate
	 * @param source
	 * @param timing
	 * @return
	 */
	private Chart readthroughChartFromL12(	Investment inv, TradeType tradeType, SamplingRate sampling, 
											String fromDate, String toDate,
											InvDataSource source, InvDataTiming timing) {		
		Chart chart = new Chart();
		
		try{	// needed because TSDB can't throw exceptions: some time series just don't exist or have data 			
			readChartFromRTL1(	inv, tradeType, sampling, 
								fromDate, toDate, 
								source, timing, 
								chart);
			
			// keep last chart in L0 memory (with data)
			String key = lookup.getChartKey(	inv, tradeType, sampling, 
												fromDate, toDate,
												source, timing);
			charts.put(key, chart);				
			
			// augment L1 with data from L2 (3rd party DB), if data not complete from origin
			readHistoryFromL2(inv);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Cache Chart READ-THROUGH: L12" + "\n" + chart.toString());
		return chart;
	}


	private void readChartFromRTL1(	Investment inv, TradeType tradeType,
									SamplingRate sampling, String fromDate, String toDate,
									InvDataSource source, InvDataTiming timing, Chart chart) {
		
		List<Candle> prices = TSDB.readPriceFromDB(		inv, tradeType, sampling, 
														fromDate, toDate,
														source, timing);
		List<Integer> sizes = TSDB.readSizeFromDB(		inv, tradeType, sampling, 
														fromDate, toDate,
														source, timing);
		
		chart.setPrices(prices);
		chart.setSizes(sizes);
		System.out.println("Cache Chart READ: L1" + "\n" + fromDate + " " + toDate + " " + chart.toString());
	}


	private void readHistoryFromL2(Investment inv) {
		System.out.println("Cache Chart READ: L2 (augment data) "  + inv.toString());

		TradeType tradeType = TradeType.TRADED; // TODO: traded?
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.HISTORICAL;
		SamplingRate scalping = SamplingRate.SCALP;

		// go from today (by tomorrow) 
		String date = getParser().getTomorrow();
		String dateAtClose = getParser().getClose(date);
		int numDays = 1; // number of days to acquire at a time
		
		while(true) { 		
			// TODO: only if it is not already in L1
			String fromDate = getParser().getDashedDateMinus(date, 1);
			Chart chart = new Chart();
			readChartFromRTL1(	inv, tradeType,
								scalping, getParser().getDashedDateMinus(date, 1), date,
								source, timing, chart);
			// check for incomplete L1 data
			if(chart.getPrices().size()<10) {		
				QuoteHistory history = readHistory(inv, dateAtClose);
				// put history in L1
				readthroughL2ToHistoricalL1(inv, history, 
											tradeType, 
											source, timing);					
				numDays--;
			}
			// go back further in time?
			if(numDays>0) {	
				date = getParser().getDashedDatePlus(date, 1);
				dateAtClose = getParser().getClose(date);
			} else {
				return;
			}
		}
	}

	private QuoteHistory readHistory(Investment inv, String dateAtClose) {
		// paceHistoricalQuery();
		QuoteHistory history = broker.readHistoricalQuotes(inv, dateAtClose); 
   
		if(history.equals("")) {
			System.out.println("WARNING: HISTORICAL DATA FARM DOWN?"); 
		} else {
			System.out.println("HISTORY " + history.toString());
		}
		return history;
	}
	

	private void readthroughL2ToHistoricalL1(Investment inv, QuoteHistory history, 
											TradeType dataType,
											InvDataSource source, InvDataTiming timing) {
		for(int i=0; i<history.quoteRows.size(); i++) {
			QuoteRow row = history.quoteRows.get(i);
			
			Long time = row.getM_time();
			Double price = row.getM_open(); 

			// read through to L1
			System.out.println("Cache History WRITE: L1 (from L2) "  + inv.toString());
			TSDB.writePrice(	time, inv, dataType, price,
								source, timing);				

			// TODO: read-through of charts to L0 happens at caller?
		}
	}

	private void paceHistoricalQuery() {
		System.out.println("...pacing historical query");
	    try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
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
	public ParseDate getParser() {
		return parseDate;
	}
	public void setParser(ParseDate parser) {
		this.parseDate = parser;
	}

}
