package com.onenow.database;

import java.util.HashMap;
import java.util.List;

import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.Sampling;
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

	private Sampling sampling;
	
	public Cache() {
		setLookup(new Lookup());
		setLastEventRT(new HashMap<String, EventRT>());
		setCharts(new HashMap<String, Chart>());
		setTSDB(new TSDB());
		setSampling(new Sampling());
	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME from broker
	public void writeEventRT(EventRT event) {

		boolean writeToMem = false;
		
		String key = getLookup().getInvestmentKey(	event.getInv(), event.getDataType(),
													event.getSource(), event.getTiming());
		
		// keep last in memory
		if(getLastEventRT().get(key) == null) { 	// never written before
			writeToMem = true;
		} else {		
			if( event.getTime() > getLastEventRT().get(key).getTime() ) {
				writeToMem = true;
			}
		}
		
		if(writeToMem) {
			getLastEventRT().put(key, event);
		}
		
		// CRITICAL PATH: fast write to ring
		writeEventToRing(event);
	}
		
	public void writeEventToRing(EventRT event) {

		Long time = event.getTime(); 
		Investment inv = event.getInv(); 
		String dataType = event.getDataType(); 
		
		InvDataSource source = event.getSource();
		InvDataTiming timing = event.getTiming();

		Double price = event.getPrice();
		int size = event.getSize();

		// TODO: INSERT RING
		// write
		getTSDB().writePrice(	time, inv, dataType, price,
								source, timing);				
		getTSDB().writeSize(time, inv, dataType, size,			
								source, timing);		
		// TODO: SQS/SNS ORCHESTRATION
		
		// update the charts
		for(String sampling:getSampling().getSamplingList("")) {
			// use miss function to force update of charts
			readThroughChartOnMissFromL1(	inv, dataType, sampling, 
											"2015-02-21", "2015-04-24",
											source, timing);
		}

	}

	
	// READ PRICE
	/**
	 * Gets the last price traded
	 * @param inv
	 * @param dataType
	 * @return
	 */
	public double readPrice(Investment inv, String dataType) {
		
		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
		
		// HIT
		String key = getLookup().getInvestmentKey(inv, dataType, source, timing);
		Double price = getLastEventRT().get(key).getPrice();  
	
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

		InvDataSource source = InvDataSource.IB;
		InvDataTiming timing = InvDataTiming.REALTIME;
		
		Chart chart = readChartFromL0(	inv, dataType, sampling, 
										fromDate, toDate,
										source, timing);	
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
	public Chart readChartFromL0(	Investment inv, String dataType, String sampling, 
									String fromDate, String toDate,
									InvDataSource source, InvDataTiming timing) {
		
		String s = "";

		// HIT? Memory is L0
		String key = getLookup().getChartKey(	inv, dataType, sampling, fromDate, toDate,
												source, timing);
		Chart chart = getCharts().get(key);
		s = "x cache HIT ";
		
		// MISS: one-off requests, ok that they take longer for now
		if(chart==null) {
			s = "x cache MISS";
			chart = new Chart();
			chart = readThroughChartOnMissFromL1(	inv, dataType, sampling, fromDate, toDate,
													source, timing);

		} 
		
//		System.out.println(s + "\n" + "IN MEM " + "\n" + chart.toString());		
		return chart;
	}


	private Chart readThroughChartOnMissFromL1(	Investment inv, String dataType, String sampling, 
												String fromDate, String toDate,
												InvDataSource source, InvDataTiming timing) {
		
		Chart chart = new Chart();
		String key = getLookup().getChartKey(	inv, dataType, sampling, fromDate, toDate,
												source, timing);
		
		try{	// needed because TSDB can't throw exceptions: some time series just don't exist or have data 
			
			// database is L1
			List<Candle> prices = getTSDB().readPriceFromDB(	inv, dataType, sampling, fromDate, toDate,
																source, timing);
			List<Integer> sizes = getTSDB().readSizeFromDB(		inv, dataType, sampling, fromDate, toDate,
																source, timing);
			
			chart.setPrices(prices);
			chart.setSizes(sizes);

			// if L1 is empty, get data from L2 (3rd party DB)
			if(chart.getPrices().size() < 5) {
				augmentL3(chart);
			}
			
			// keep last in L0 memory (with data)
			if(!chart.getSizes().isEmpty() && !chart.getPrices().isEmpty()) { // TODO: both not empty?
				getCharts().put(key, chart);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("x READ-THROUGH " + "\n" + chart.toString());
		return chart;
	}


	private void augmentL3(Chart chart) {
		System.out.println("Small chart: " + chart.getPrices().size());
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


	public Sampling getSampling() {
		return sampling;
	}


	public void setSampling(Sampling sampling) {
		this.sampling = sampling;
	}

}
