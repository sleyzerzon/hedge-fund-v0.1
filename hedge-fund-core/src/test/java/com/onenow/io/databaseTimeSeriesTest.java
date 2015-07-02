package com.onenow.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Serie;
import org.testng.Assert;
import org.testng.annotations.Test;

import backtype.storm.event__init;

import com.ib.client.Types.BarSize;
import com.onenow.constant.ColumnName;
import com.onenow.constant.DBQuery;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityHistory;
import com.onenow.data.EventActivityRealtime;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestHistory;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.research.Candle;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class databaseTimeSeriesTest {

	EventActivityRealtime realtimeActivity = new EventActivityRealtime();	
	
	EventActivityRealtime greekActivity;
	
	// EventActivitySize;
	
  @Test
  public void dbConnect() {
	  InfluxDB db = DBTimeSeries.dbConnect();
	  Assert.assertTrue(db!=null);
  }
  
  private EventActivityHistory getHistoryActivity() {
	  
	  int reqId = 123; 
	  long time = TimeParser.getTimestampNow()/1000; 
	  // price
	  double high = 0.33; 
	  double low = 0.09; 
	  double open = 0.12; 
	  double close = 0.28; 
	  // etc
	  double wap = 0.0; 
	  long volume = 3; 
	  int count = 23;		
		
	  EventActivityHistory event = new EventActivityHistory(reqId, time, high, low, open, close, wap, volume, count);	

	  event.setInvestment(new InvestmentStock(new Underlying("PABLO")));
	  event.tradeType = TradeType.BUY;
	  event.source = InvDataSource.AMERITRADE;
	  event.timing = InvDataTiming.HISTORICAL;
	  
	  return event;
  }
  
  @Test
  public void writePrice() {
	  	  
	  EventActivityHistory historyActivity = getHistoryActivity();

	  String serieName = Lookup.getEventKey(historyActivity);
	  List<Serie> series = DBTimeSeriesPrice.getWriteSerie(historyActivity, serieName);
	  
	  testColumns(serieName, series);

	  	// write
		DBTimeSeriesPrice.write(historyActivity);
		
		TimeParser.wait(5); // wait for write thread to complete
		
		EventRequest requestHigh = new EventRequest(DBQuery.MAX, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", historyActivity);
		EventRequest requestLow = new EventRequest(DBQuery.MIN, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", historyActivity);
		EventRequest requestOpen = new EventRequest(DBQuery.FIRST, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", historyActivity);
		EventRequest requestClose = new EventRequest(DBQuery.LAST, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", historyActivity);

		List<Candle> candlesHigh = new ArrayList<Candle>();
		List<Candle> candlesLow = new ArrayList<Candle>();
		List<Candle> candlesOpen = new ArrayList<Candle>();
		List<Candle> candlesClose = new ArrayList<Candle>();

		try {
			candlesHigh = DBTimeSeriesPrice.read(requestHigh);
			candlesLow = DBTimeSeriesPrice.read(requestLow);
			candlesOpen = DBTimeSeriesPrice.read(requestOpen);
			candlesClose = DBTimeSeriesPrice.read(requestClose);
		} catch (Exception e) {
		}
		
		Watchr.info("READ CANDLES " + candlesHigh);
		Watchr.info("READ CANDLES " + candlesLow);
		Watchr.info("READ CANDLES " + candlesOpen);
		Watchr.info("READ CANDLES " + candlesClose);
		
//		int lastCandleHigh = candlesHigh.size()-1;
//		int lastCandleLow = candlesLow.size()-1;
//		int lastCandleOpen = candlesOpen.size()-1;
//		int lastCandleClose = candlesClose.size()-1;
		
		Assert.assertTrue(candlesHigh.get(0).openPrice.equals(historyActivity.high));
		Assert.assertTrue(candlesLow.get(0).openPrice.equals(historyActivity.low));
		Assert.assertTrue(candlesOpen.get(0).openPrice.equals(historyActivity.open));
		Assert.assertTrue(candlesClose.get(0).openPrice.equals(historyActivity.close));		
			
  }

private void testColumns(String serieName, List<Serie> series) {
	Serie serie = series.get(0);
	  
	  String columns = "";
	  for(String column:serie.getColumns()) {
		  columns = columns + column.toString() + " ";
	  }
	  Watchr.log("WRITE SERIES COLUMNS: " + columns.toString());		  
	 	  
		Assert.assertNotNull(serie.getColumns());	
		Assert.assertEquals(serie.getColumns().length, 10);
		Assert.assertEquals(serie.getColumns()[0], ColumnName.TIME.toString());
		Assert.assertEquals(serie.getColumns()[1], ColumnName.PRICE.toString());
		Assert.assertEquals(serie.getColumns()[2], ColumnName.SOURCE.toString());
		Assert.assertEquals(serie.getColumns()[3], ColumnName.TIMING.toString());
		Assert.assertEquals(serie.getColumns()[4], ColumnName.TRADETYPE.toString());
		Assert.assertEquals(serie.getColumns()[5], ColumnName.UNDERLYING.toString());
		Assert.assertEquals(serie.getColumns()[6], ColumnName.INVTYPE.toString());
		Assert.assertEquals(serie.getColumns()[7], ColumnName.OPTIONSTRIKE.toString());
		Assert.assertEquals(serie.getColumns()[8], ColumnName.OPTIONEXP.toString());
		Assert.assertEquals(serie.getColumns()[9], ColumnName.FUTUREEXP.toString());

		Assert.assertNotNull(serie.getRows());
		Assert.assertEquals(serie.getRows().size(), 1);
		Assert.assertEquals(serie.getRows().get(0).size(), 10);
		
}
  
  @Test
  public void writeSize() {
	  // contextualizeEvent(realtimeActivity);


  }
  
  @Test
  public void writeGreek() {
	  // contextualizeEvent(greekActivity);


  }
  
}
