package com.onenow.database;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.TradingRate;
import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;
import com.onenow.util.ParseDate;

public class Cache {
	
	private TSDB 									TSDB;			// database

	private Lookup 									lookup;			// key
	
	private HashMap<String, EventRT>				lastEventRT; 	// last set of price/size/etc
	private HashMap<String, Chart>					charts;			// price history in chart format
	
	private ParseDate	parser = new ParseDate();

	
	public Cache() {
		setLookup(new Lookup());
		setLastEventRT(new HashMap<String, EventRT>());
		setCharts(new HashMap<String, Chart>());
		setTSDB(new TSDB());

	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME from broker
	public void writeEventRT(EventRT event) {

		String key = getLookup().getInvestmentKey(event.getInv(), event.getDataType());

		// keep last in memory
		if( event.getTime() > getLastEventRT().get(key).getTime() ) {
			getLastEventRT().put(key, event);
		}
		
		// CRITICAL PATH: fast write to ring
		writeEventToRing(event);
	}
		
	public void writeEventToRing(EventRT event) {

		Long time = event.getTime(); 
		Investment inv = event.getInv(); 
		String dataType = event.getDataType(); 

		Double price = event.getPrice();
		int size = event.getSize();

		// TODO: INSERT RING AND EVENTS HERE.  Write to different rings instead (orchestrator, investor...)
		getTSDB().writePrice(time, inv, dataType, price);	// write
		// TODO: SQS/SNS ORCHESTRATION
		getTSDB().writeSize(time, inv, dataType, size);		// write
		// TODO: SQS/SNS ORCHESTRATION

	}

	
	// READ PRICE
	/**
	 * Gets the last price traded
	 * @param inv
	 * @param dataType
	 * @return
	 */
	public double readPrice(Investment inv, String dataType) {
		
		// HIT
		String key = getLookup().getInvestmentKey(inv, dataType);
		Double price = getLastEventRT().get(key).getPrice();  // HIT
	
		// MISS: fill with the last data from chart until RT vents start to hit
		if(price==null) {
			List<Candle> candles = readPrice(	inv, TradeType.TRADED.toString(), SamplingRate.SCALP.toString(), 
												getParser().getToday(), getParser().getToday()); 
			Candle last = candles.get(candles.size()-1);
			price = last.getClosePrice();
		} 
		
		return price;
	}
	
	/**
	 * Gets the price for a time window
	 * @param inv
	 * @param dataType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public List<Candle> readPrice(	Investment inv, String dataType, String sampling, 
									String fromDate, String toDate) {

		Chart chart = readChart(inv, dataType, sampling, fromDate, toDate);	
		return chart.getPrices();
	}
	
	// READ CHARTS
	/**
	 * One-off chart read creates a chart from size and price information from the memory/database
	 * @param inv
	 * @param dataType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public Chart readChart(	Investment inv, String dataType, String sampling, 
							String fromDate, String toDate) {

		// HIT
		String key = getLookup().getChartKey(inv, dataType, sampling, fromDate, toDate);
		Chart chart = getCharts().get(key);
		
		// MISS: one-off requests, ok that they take longer for now
		if(chart==null) {
			
			chart = new Chart();
			
			// TODO IMPORTANT get from cache, and if not available get from DB
			try{
				List<Candle> prices = getTSDB().readPriceFromDB(inv, dataType, sampling, fromDate, toDate);
				List<Integer> sizes = getTSDB().readSizeFromDB(inv, dataType, sampling, fromDate, toDate);
				
				chart.setPrices(prices);
				chart.setSizes(sizes);

				// keep last in memory
				getCharts().put(key, chart);

			} catch (Exception e) {
				e.printStackTrace();
			}

		} 
		
		return chart;
	}				

	
	// TEST
	
	// PRINT
	
	// TODO: print all the data in memory, not just prices, from Maps/Ring/etc
	public String toString() {
		String s="";
		s = getLastEventRT().toString();
		return s;
	}
	
	
	// SET GET
	public Lookup getLookup() {
		return lookup;
	}

	public void setLookup(Lookup lookup) {
		this.lookup = lookup;
	}

	public HashMap<String, EventRT> getLastEventRT() {
		return lastEventRT;
	}

	public void setLastEventRT(HashMap<String, EventRT> lastEventRT) {
		this.lastEventRT = lastEventRT;
	}


	public HashMap<String, Chart> getCharts() {
		return charts;
	}


	public void setCharts(HashMap<String, Chart> charts) {
		this.charts = charts;
	}


	public ParseDate getParser() {
		return parser;
	}


	public void setParser(ParseDate parser) {
		this.parser = parser;
	}

	public TSDB getTSDB() {
		return TSDB;
	}

	public void setTSDB(TSDB tSDB) {
		TSDB = tSDB;
	}

}
