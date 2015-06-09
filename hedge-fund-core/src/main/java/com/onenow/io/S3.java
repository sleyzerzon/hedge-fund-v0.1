package com.onenow.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;

import com.amazonaws.util.StringUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.onenow.admin.InitAmazon;
import com.onenow.util.Watchr;


public class S3 {
	
	public static AmazonS3 connection = InitAmazon.getS3Connection();
	
	public S3 () {

	}

	/** 
	 * Output example:
	 * 		mahbuckat1   2011-04-21T18:05:39.000Z
	 * 		mahbuckat2   2011-04-21T18:05:48.000Z
	 * 		mahbuckat3   2011-04-21T18:07:18.000Z
	 * @return
	 */
	public static List<Bucket> listBuckets() {
		List<Bucket> buckets = connection.listBuckets();
		for (Bucket bucket : buckets) {
		       Watchr.log(Level.INFO, bucket.getName() + "\t\t" + StringUtils.fromDate(bucket.getCreationDate()));
		}
		return buckets;
	}
	
	public static Bucket createBucket(String name) {
		Bucket bucket = null;
		try {
			bucket = connection.createBucket(name);
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, "Cannot create bucket");
		}
		return bucket;
	}
	
	/**
	 * Output example: 
	 * 		myphoto1.jpg 251262  2011-08-08T21:35:48.000Z
	 * 		myphoto2.jpg 262518  2011-08-08T21:38:01.000Z
	 * @param bucket
	 */
	public static void listObjects(Bucket bucket) {

		ObjectListing objects = connection.listObjects(bucket.getName());
		do {
		        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
		        	Watchr.log(Level.INFO, 	bucket.getName() + " CONTAINS: " +   
		        							objectSummary.getKey() + "\t" +
        									"modified " + StringUtils.fromDate(objectSummary.getLastModified()) + "\t" +
		                					"size " + objectSummary.getSize() + "\t"
		                					);
		        }
		        objects = connection.listNextBatchOfObjects(objects);
		 } while (objects.isTruncated());
	}
	
	private static void deleteBucket(Bucket bucket) {
		connection.deleteBucket(bucket.getName());
	}
	
	/**
	 * This creates an object (i.e. hello.txt) with a content string (i.e. "Hello World!")
	 * @param bucket
	 * @param objectContent
	 * @param objectName
	 */
	public static void createObject(Bucket bucket, String objectContent, String objectName) {
		ByteArrayInputStream input = new ByteArrayInputStream(objectContent.getBytes());
		connection.putObject(bucket.getName(), objectName, input, new ObjectMetadata());
	}	
	
	private static void chmodPrivate(Bucket bucket, String objectName) {
		connection.setObjectAcl(bucket.getName(), objectName, CannedAccessControlList.Private);
	}
	
	private static void chmodPublic(Bucket bucket, String objectName) {
		connection.setObjectAcl(bucket.getName(), objectName, CannedAccessControlList.PublicRead);		
	}
	
	/**
	 * This downloads the object (i.e. "perl_poetry.pdf") and saves it in the file (i.e. "/home/larry/documents/perl_poetry.pdf")
	 */
	private static void object2File(Bucket bucket, String objectName, String fileName) {
		connection.getObject(
		        				new GetObjectRequest(bucket.getName(), objectName),
		        				new File(fileName)
		);
	}
	
	/**
	 * This deletes the object (i.e. goodbye.txt)
	 * @param bucket
	 * @param objectName
	 */
	private static void deleteObject(Bucket bucket, String objectName) {
		connection.deleteObject(bucket.getName(), objectName);
	}

	/**
	 * It works if the bucket object is Public.
	 * Signed download URLs will work for the time period even if the object is private (when the time period is up, the URL will stop working)
	 * Output example: https://my-bucket-name.objects.dreamhost.com/secret_plans.txt?Signature=XXXXXXXXXXXXXXXXXXXXXXXXXXX&Expires=1316027075&AWSAccessKeyId=XXXXXXXXXXXXXXXXXXX
	 * @param bucket
	 * @param fileName
	 * @return
	 */
	private static URL getDownloadURL(Bucket bucket, String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), fileName);
		URL url;
		try {
			url = new URL("");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		url = connection.generatePresignedUrl(request);
		Watchr.log(Level.INFO, url.toString());
		return url;
	}

}
