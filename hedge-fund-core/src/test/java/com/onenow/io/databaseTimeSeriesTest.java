package com.onenow.io;

import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Serie;
import org.testng.Assert;
import org.testng.annotations.Test;

import backtype.storm.event__init;

import com.ib.client.Types.BarSize;
import com.onenow.constant.ColumnName;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityHistory;
import com.onenow.data.EventActivityRealtime;
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
	  long time = TimeParser.getTimestampNow()*1000; 
	  double high = 0.23; 
	  double low = 0.19; 
	  double open = 0.12; 
	  double close = 0.28; 
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
	  Serie serie = DBTimeSeriesPrice.getWriteSerie(historyActivity, serieName);
	 
	  Watchr.log("WRITE INTO " + serieName + " SERIE " + serie.toString());

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

		
		DBTimeSeriesPrice.writeThread(historyActivity, serie);
		
		TimeParser.wait(5); // wait for write thread to complete
		
		EventRequestHistory requestHistory = new EventRequestHistory(historyActivity, "-1m" ,"now()");

		List<Candle> candles = DBTimeSeriesPrice.read(requestHistory);
		Watchr.info("READ CANDLES " + candles);
		
		int lastCandle = candles.size()-1;
		Assert.assertTrue(candles.get(lastCandle).openPrice.equals(historyActivity.open));
		
			
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
