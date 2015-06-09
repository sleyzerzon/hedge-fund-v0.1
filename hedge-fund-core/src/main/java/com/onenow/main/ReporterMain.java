package com.onenow.main;

import java.util.List;

import com.amazonaws.services.s3.model.Bucket;
import com.onenow.io.S3;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.TimeParser;
import com.onenow.util.Watchr;

public class ReporterMain {

	
	public static void main(String[] args) {

		FlexibleLogger.setup();
		
		String bucketName = "hedge-reporter";
		Bucket bucket = S3.createBucket(bucketName);
		
		List<Bucket> buckets = S3.listBuckets();
		
		String startDate = TimeParser.getDashedToday();
		Integer numDays = 3;
		
		for(int days=0; days<numDays; days++) {

			String date = TimeParser.getDashedDateMinus(startDate, 1);
			
			// Watchr.log("DATE " + date);
			
			String objectName = "holaS3";
			
			String objectContent = date;
			
			S3.createObject(bucket, objectContent, objectName);
						
		}
		
		S3.listObjects(bucket);
		
	}

}
