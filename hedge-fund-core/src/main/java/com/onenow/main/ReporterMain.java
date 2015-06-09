package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.constant.InvDataSource;
import com.onenow.constant.InvDataTiming;
import com.onenow.constant.SamplingRate;
import com.onenow.constant.TradeType;
import com.onenow.data.InitMarket;
import com.onenow.instrument.Investment;
import com.onenow.io.Lookup;
import com.onenow.io.S3;
import com.onenow.io.databaseTimeSeries;
import com.onenow.portfolio.Portfolio;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ReporterMain {

	
	public static void main(String[] args) {

		FlexibleLogger.setup();
				
		Portfolio marketPortfolio = InitMarket.getTestPortfolio();

		String bucketName = "hedge-reporter";
		Bucket bucket = S3.createBucket(bucketName);
		
		List<Bucket> buckets = S3.listBuckets();
		
		String toDate = TimeParser.getTodayDashed();
		Integer numDays = 30;
		
		writeInvestmentPriceIntoBucket(marketPortfolio, bucket, toDate,
				numDays);
	}

	private static void writeInvestmentPriceIntoBucket(
		Portfolio marketPortfolio, Bucket bucket, String toDate,
		Integer numDays) {
		for(int days=0; days<numDays; days++) {
			String fromDate = TimeParser.getDateMinusDashed(toDate, 1);
			for (Investment inv:marketPortfolio.investments) {				
				writeInvestmentDayPriceIntoBucket(inv, bucket, fromDate, toDate);
			}
			toDate = TimeParser.getDateMinusDashed(toDate, 1);
		}
		S3.listObjects(bucket);
	}

	private static void writeInvestmentDayPriceIntoBucket(Investment inv,
		Bucket bucket, String fromDate, String toDate) {
		TradeType tradeType = TradeType.TRADED;
		SamplingRate samplingRate = SamplingRate.SCALPMEDIUM;
		InvDataSource source = InvDataSource.IB;  
		InvDataTiming timing = InvDataTiming.REALTIME; 
		
		try{	// some time series just don't exist or have data 			
			
			String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);
			String fileName = key+"-"+toDate;
			Watchr.log(Level.INFO, "working on: " + fileName);
			List<Serie> series = databaseTimeSeries.readPriceSeriesFromDB(key, samplingRate, fromDate, toDate);				
			S3.createObject(bucket, series.toString(), fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
