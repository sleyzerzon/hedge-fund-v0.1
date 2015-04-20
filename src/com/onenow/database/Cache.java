package com.onenow.database;

import java.util.HashMap;
import java.util.List;

import com.onenow.instrument.Investment;
import com.onenow.research.Candle;
import com.onenow.research.Chart;

public class Cache {
	
	Lookup 									lookup;			// key
	
	HashMap<String, EventRT>				lastEventRT; 	// last set of price/size/etc
	HashMap<String, Chart>					charts;			// price history in chart format
	
	Ring 									ring;
	
	public Cache() {
		setLookup(new Lookup());
		setLastEventRT(new HashMap<String, EventRT>());
		setCharts(new HashMap<String, Chart>());
		setRing(new Ring());
	}
	
	
	// PUBLIC
	// TODO: continuous queries http://influxdb.com/docs/v0.8/api/continuous_queries.html
	
	// REAL-TIME
	public void writeEventRT(EventRT event) {

		String key = getLookup().getInvestmentKey(event.getInv(), event.getDataType());

		// keep last in memory
		if( event.getTime() > getLastEventRT().get(key).getTime() ) {
			getLastEventRT().put(key, event);
		}
		
		// fast write to ring
		getRing().writeEventRT(event);
	}
	
	// READ PRICE
	/**
	 * Gets the latest real-time price
	 * @param inv
	 * @param dataType
	 * @return
	 */
	public double readPrice(Investment inv, String dataType) {
		
		String key = getLookup().getInvestmentKey(inv, dataType);
		
		Double price = getLastEventRT().get(key).getPrice();
		
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
	public List<Candle> readPrice(	Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {

		Chart chart = readChart(inv, dataType, fromDate, toDate, sampling);
		
		return chart.getPrices();
	}
	
	// READ CHARTS
	/**
	 * Creates a chart from size and price information from the ring/database
	 * @param inv
	 * @param dataType
	 * @param fromDate
	 * @param toDate
	 * @param sampling
	 * @return
	 */
	public Chart readChart(Investment inv, String dataType, 
			String fromDate, String toDate, String sampling) {

		String key = getLookup().getChartKey(inv, dataType, fromDate, toDate, sampling);
		Chart chart = new Chart();
		
		if( getCharts().get(key) == null ) {

			System.out.println("CHART NULL");
			
			// TODO IMPORTANT get from cache, and if not available get from DB
			List<Candle> prices = getRing().readPrice(inv, dataType, fromDate, toDate, sampling);
			List<Integer> sizes = getRing().readSize(inv, dataType, fromDate, toDate, sampling);
			chart.setPrices(prices);
			chart.setSizes(sizes);

			// keep last in memory
			getCharts().put(key, chart);

		} else {
			chart = getCharts().get(key);
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

	public Ring getRing() {
		return ring;
	}

	public void setRing(Ring ring) {
		this.ring = ring;
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


}
