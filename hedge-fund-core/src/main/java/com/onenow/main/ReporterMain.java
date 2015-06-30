package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.EventActivityHistory;
import com.onenow.data.EventRequestHistory;
import com.onenow.data.EventRequestRealtime;
import com.onenow.data.InitMarket;
import com.onenow.instrument.Investment;
import com.onenow.io.DBTimeSeriesPrice;
import com.onenow.io.Lookup;
import com.onenow.io.S3;
import com.onenow.io.DBTimeSeries;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.SysProperties;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ReporterMain {

	
	public static void main(String[] args) {

		InitLogger.run("");
				
		Portfolio marketPortfolio = InitMarket.getPrimaryPortfolio();

		String bucketName = "hedge-reporter";
		Bucket bucket = S3.createBucket(bucketName);
		
		List<Bucket> buckets = S3.listBuckets();
		
		String toDate = TimeParser.getTodayDashed();
		Integer numDays = 30;
		
		writeInvestmentPriceIntoBucket(marketPortfolio, bucket, toDate, numDays);
	}

	private static void writeInvestmentPriceIntoBucket(Portfolio marketPortfolio, Bucket bucket, String toDate, Integer numDays) {
		for(int days=0; days<numDays; days++) {
			String fromDate = TimeParser.getDateMinusDashed(toDate, 1);
			for (Investment inv:marketPortfolio.investments) {				
				writeInvestmentDayPriceIntoBucket(inv, bucket, fromDate, toDate);
			}
			toDate = TimeParser.getDateMinusDashed(toDate, 1);
			
			TimeParser.wait(1); // TODO: pace
		}
		S3.listObjects(bucket);
	}

	private static void writeInvestmentDayPriceIntoBucket(Investment inv, Bucket bucket, String fromDashedDate, String toDashedDate) {
		
		
		EventRequestRealtime request = new EventRequestRealtime(	inv, InvDataSource.IB, InvDataTiming.REALTIME,
																	SamplingRate.SCALPMEDIUM, TradeType.TRADED, 
																	fromDashedDate, toDashedDate);
		// REQUEST: -UNDER AAPL -TYPE STOCK -SOURCE NULL -TIMING NULL -TRADETYPE NULL -SAMPLING NULL -FROM 2015-06-26 -TO 2015-06-27 -SOURCE IB -TIMING REALTIME -SAMPLING SCALPMEDIUM -TRADETYPE TRADE
		
		
		String key = Lookup.getEventKey(request);
		String fileName = key+"-"+toDashedDate;
		Watchr.log(Level.INFO, "working on: " + fileName + " for request: " + request.toString());
		List<Serie> series;
		try {
			series = DBTimeSeriesPrice.readSeries(request);
			S3.createObject(bucket, series.toString(), fileName);
		} catch (Exception e) {
		}				
	}

}
