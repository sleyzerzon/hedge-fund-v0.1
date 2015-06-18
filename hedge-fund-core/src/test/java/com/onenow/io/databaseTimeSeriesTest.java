package com.onenow.io;

import java.util.List;
import java.util.Map;

import org.influxdb.InfluxDB;
import org.influxdb.dto.Serie;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.onenow.constant.ColumnName;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityHistory;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.util.Watchr;

public class databaseTimeSeriesTest {

	int reqId = 123; 
	long time = 12346; 
	double high = 0.23; 
	double low = 0.19; 
	double open = 0.12; 
	double close = 0.28; 
	double wap = 0.0; 
	long volume = 3; 
	int count = 23;
	
	EventActivityHistory activity = new EventActivityHistory(reqId, time, high, low, open, close, wap, volume, count);

	
  @Test
  public void dbConnect() {
	  InfluxDB db = DBTimeSeries.dbConnect();
	  Assert.assertTrue(db!=null);
  }
  
  @Test
  public void writePrice() {
	  	  
	  activity.setInvestment(new InvestmentStock(new Underlying("PABLO")));
		activity.tradeType = TradeType.BUY;
		activity.source = InvDataSource.AMERITRADE;
		activity.timing = InvDataTiming.HISTORICAL;


	  Serie serie = DBTimeSeriesSize.getWriteSerie(activity, "hola db");
	  
	  for(String column:serie.getColumns()) {
		  Watchr.log("COLUMNS: " + column.toString());		  
	  }
	  
	  Watchr.log("SERIE " + serie.toString());
	  
		Assert.assertNotNull(serie.getColumns());	
		Assert.assertEquals(serie.getColumns().length, 10);
		Assert.assertEquals(serie.getColumns()[0], ColumnName.TIME.toString());
		Assert.assertEquals(serie.getColumns()[1], ColumnName.SIZE.toString());
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

		
		DBTimeSeriesPrice.writeThread(activity, serie);
		
		
		// test iterator
		List<Map<String, Object>> row = serie.getRows();
		

//		Assert.assertTrue(iterator.hasNext());
//		Row first = iterator.next();
		
//		Assert.assertEquals(row.getColumn(0), 1l);
//		Assert.assertEquals(first.getColumn(1), 96.3f);
//		Assert.assertEquals(first.getColumn(2), "no error");

//		Assert.assertTrue(iterator.hasNext());
//		Row second = iterator.next();
//		Assert.assertEquals(second.getColumn("time"), 2l);
//		Assert.assertEquals(second.getColumn("idle"), 69.5f);
//		Assert.assertEquals(second.getColumn("error"), "with error");

//		Assert.assertFalse(iterator.hasNext());
		
  }
}
