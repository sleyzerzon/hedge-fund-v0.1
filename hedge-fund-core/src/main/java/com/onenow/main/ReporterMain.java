package com.onenow.main;

import java.util.List;
import java.util.logging.Level;

import org.influxdb.dto.Serie;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.admin.NetworkConfig;
import com.onenow.constant.DeployEnvironment;
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
				
		Portfolio marketPortfolio = InitMarket.getHistoryPortfolio();

		String bucketName = "hedge-reporter-"+getBucketSubfolder().toString().toLowerCase();
		Bucket bucket = S3.createBucket(bucketName);
		
		List<Bucket> buckets = S3.listBuckets();
		
		String toDate = "2015-06-30";
		Integer numDays = 3;
		
		writeInvestmentPriceIntoBucket(marketPortfolio, bucket, toDate, numDays);
	}

	private static void writeInvestmentPriceIntoBucket(Portfolio marketPortfolio, Bucket bucket, String toDashedDate, Integer numDays) {
		
		for(int days=0; days<numDays; days++) {

			for (Investment inv:marketPortfolio.investments) {				
				writeInvestmentDayPriceIntoBucket(inv, bucket, toDashedDate);
			}
			toDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
			
			TimeParser.wait(1); // TODO: pace
		}
		// S3.listObjects(bucket);
	}

	private static void writeInvestmentDayPriceIntoBucket(Investment inv, Bucket bucket, String toDashedDate) {
		
		String fromDashedDate = TimeParser.getDateMinusDashed(toDashedDate, 1);
		
		EventRequestRealtime request = new EventRequestRealtime(	inv, InvDataSource.IB, InvDataTiming.REALTIME,
																	SamplingRate.SCALPMEDIUM, TradeType.TRADED, 
																	fromDashedDate, toDashedDate);
				
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

	private static DeployEnvironment getBucketSubfolder() {
		
		DeployEnvironment subfolder = DeployEnvironment.DEVELOPMENT;
		
		if(!NetworkConfig.isMac()) {
			// TODO handling for production environment
			subfolder = DeployEnvironment.STAGING;
		}
		
		return subfolder;
	}

}
