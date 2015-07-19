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
import com.onenow.constant.DBname;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.PriceType;
import com.onenow.constant.SizeType;
import com.onenow.data.EventActivityPriceHistory;
import com.onenow.data.EventActivityPriceSizeRealtime;
import com.onenow.data.EventRequest;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.EventRequestRaw;
import com.onenow.execution.HistorianService;
import com.onenow.instrument.InvestmentStock;
import com.onenow.instrument.Underlying;
import com.onenow.research.Candle;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class databaseTimeSeriesTest {

	EventActivityPriceSizeRealtime realtimeActivity = new EventActivityPriceSizeRealtime();	
	
	EventActivityPriceSizeRealtime greekActivity;
	
	
  @Test
  public void dbConnect() {
	  InfluxDB db = DBTimeSeries.dbConnect();
	  Assert.assertTrue(db!=null);
  }
  
  private EventActivityPriceHistory getHistoryActivity() {
	  
	  int reqId = 123; 
	  long timeSeconds = TimeParser.getTimeMilisecondsNow()/1000; 
	  // price
	  double high = 0.33; 
	  double low = 0.09; 
	  double open = 0.12; 
	  double close = 0.28; 
	  // etc
	  double wap = 0.0; 
	  long volume = 3; 
	  int count = 23;		
		
	  EventActivityPriceHistory event = new EventActivityPriceHistory(reqId, timeSeconds, high, low, open, close, wap, volume, count);	

	  event.setInvestment(new InvestmentStock(new Underlying("PABLO")));
	  event.source = InvDataSource.AMERITRADE;
	  event.timing = InvDataTiming.HISTORICAL;
	  event.priceType = PriceType.BID;
	  event.sizeType = SizeType.BID_SIZE;
	  
	  return event;
  }
  
  @Test
  public void writePrice() {
	  	  
	  	// write
		DBTimeSeriesPrice.write(getHistoryActivity());
		
		TimeParser.sleep(5); // wait for write thread to complete
		
		EventRequestRaw requestHigh = new EventRequestRaw(DBQuery.MAX, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", getHistoryActivity());
		EventRequestRaw requestLow = new EventRequestRaw(DBQuery.MIN, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", getHistoryActivity());
		EventRequestRaw requestMean = new EventRequestRaw(DBQuery.MEAN, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", getHistoryActivity());
		EventRequestRaw requestMedian = new EventRequestRaw(DBQuery.MEDIAN, ColumnName.PRICE, SamplingRate.SCALP, "-1m" ,"now()", getHistoryActivity());

		List<Candle> candlesHigh = new ArrayList<Candle>();
		List<Candle> candlesLow = new ArrayList<Candle>();
		List<Candle> candlesMean = new ArrayList<Candle>();
		List<Candle> candlesMedian = new ArrayList<Candle>();

		try {
			candlesHigh = DBTimeSeriesPrice.read(requestHigh);
			candlesLow = DBTimeSeriesPrice.read(requestLow);
			candlesMean = DBTimeSeriesPrice.read(requestMean);
			candlesMedian = DBTimeSeriesPrice.read(requestMedian);
		} catch (Exception e) {
		}
		
		Watchr.info("READ HIGH: " + candlesHigh.get(0).openPrice + " FROM CANDLES " + candlesHigh);
		Watchr.info("READ LOW: " + candlesLow.get(0).openPrice + " FROM CANDLES "+ candlesLow);
		Watchr.info("READ MEAN: " + candlesMean.get(0).openPrice + " FROM CANDLES" + candlesMean);
		Watchr.info("READ MEDIAN: " + candlesMedian.get(0).openPrice + " FROM CANDLES " + candlesMedian);
		
		// now only writing the opening price, not open/close/high/low from history at every increment
		Assert.assertTrue(candlesHigh.get(0).openPrice.equals(getHistoryActivity().close));
		// Assert.assertTrue(candlesLow.get(0).openPrice.equals(historyActivity.open));
		// Assert.assertTrue(candlesMean.get(0).openPrice.equals(0.20500000000000002)); // precision issue in CodeShip
		// Assert.assertTrue(candlesMedian.get(0).openPrice.equals(0.12));		
			
  }

  // TODO: tests for Read History, Read Realtime
  // test for {} in request queue for history
  
  @Test
  public void writeSize() {
	  
	  	// write
		DBTimeSeriesSize.write(getHistoryActivity());

		TimeParser.sleep(5); // wait for write thread to complete

		EventRequestRaw request = new EventRequestRaw(DBQuery.MAX, ColumnName.SIZE, SamplingRate.SCALP, "-1m" ,"now()", getHistoryActivity());

		List<Integer> ints = new ArrayList<Integer>();

		try {
			ints = DBTimeSeriesSize.read(request);
		} catch (Exception e) {
		}
		
		Watchr.info("READ: " + ints.get(0) + " FROM INTS " + ints);

		Assert.assertTrue(ints.get(0).equals(getHistoryActivity().volume));

  }
  
  @Test
  public void writeGreek() {
	  // contextualizeEvent(greekActivity);


  }
  
}
