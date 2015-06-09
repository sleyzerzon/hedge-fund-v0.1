package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.constant.DBname;
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
import com.onenow.research.Candle;
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
		
		for(int days=0; days<numDays; days++) {

			String fromDate = TimeParser.getDateMinusDashed(toDate, 1);
			
			for (Investment inv:marketPortfolio.investments) {
				
				TradeType tradeType = TradeType.TRADED;
				SamplingRate samplingRate = SamplingRate.SCALPMEDIUM;
				InvDataSource source = InvDataSource.IB;  
				InvDataTiming timing = InvDataTiming.REALTIME; 
				
				try{	// some time series just don't exist or have data 			
					
					String key = Lookup.getInvestmentKey(inv, tradeType, source, timing);
					List<Serie> series = databaseTimeSeries.queryPrice(DBname.PRICE.toString(), key, samplingRate, fromDate, toDate);

					Watchr.log(Level.INFO, series.toString());
									
					S3.createObject(bucket, series.toString(), key+"-"+toDate);
					
				} catch (Exception e) {
					Watchr.log(Level.SEVERE, e.getMessage());
				}
				
			}
			
			toDate = TimeParser.getDateMinusDashed(toDate, 1);

		}
		
		S3.listObjects(bucket);
		
	}

}
