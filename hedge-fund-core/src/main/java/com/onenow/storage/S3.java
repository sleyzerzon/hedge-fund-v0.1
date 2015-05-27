package com.onenow.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.util.StringUtils;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3ObjectSummary;


public class S3 {

	String accessKey = "insert your access key here!";
	String secretKey = "insert your secret key here!";

	AmazonS3 connection = getConnection();
	
	public S3 () {

		setEndpoint();

		List<Bucket> buckets = listBuckets();
	}
	
	private AmazonS3 getConnection() {
	
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);

		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint("endpoint.com");
		
		return conn;
	}

	private void setEndpoint() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		connection.setEndpoint("objects.dreamhost.com");
	}

	/** 
	 * Output example:
	 * 		mahbuckat1   2011-04-21T18:05:39.000Z
	 * 		mahbuckat2   2011-04-21T18:05:48.000Z
	 * 		mahbuckat3   2011-04-21T18:07:18.000Z
	 * @return
	 */
	private List<Bucket> listBuckets() {
		List<Bucket> buckets = connection.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
		return buckets;
	}
	
	private void createBucket(String name) {
		Bucket bucket = connection.createBucket(name);
	}
	
	/**
	 * Output example: 
	 * 		myphoto1.jpg 251262  2011-08-08T21:35:48.000Z
	 * 		myphoto2.jpg 262518  2011-08-08T21:38:01.000Z
	 * @param bucket
	 */
	private void listObjects(Bucket bucket) {
		ObjectListing objects = connection.listObjects(bucket.getName());
		do {
		        for (S3ObjectSummary objectSummary : objects.getObjectSummaries()) {
		                System.out.println(objectSummary.getKey() + "\t" +
		                		objectSummary.getSize() + "\t" +
		                        StringUtils.fromDate(objectSummary.getLastModified()));
		        }
		        objects = connection.listNextBatchOfObjects(objects);
		} while (objects.isTruncated());
	}
	
	private void deleteBucket(Bucket bucket) {
		connection.deleteBucket(bucket.getName());
	}
	
	/**
	 * This creates an object (i.e. hello.txt) with a content string (i.e. "Hello World!")
	 * @param bucket
	 * @param content
	 * @param objectName
	 */
	private void createObject(Bucket bucket, String content, String objectName) {
		ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes());
		connection.putObject(bucket.getName(), objectName, input, new ObjectMetadata());
	}	
	
	private void chmodPrivate(Bucket bucket, String objectName) {
		connection.setObjectAcl(bucket.getName(), objectName, CannedAccessControlList.Private);
	}
	
	private void chmodPublic(Bucket bucket, String objectName) {
		connection.setObjectAcl(bucket.getName(), objectName, CannedAccessControlList.PublicRead);		
	}
	
	/**
	 * This downloads the object (i.e. "perl_poetry.pdf") and saves it in the file (i.e. "/home/larry/documents/perl_poetry.pdf")
	 */
	private void object2File(Bucket bucket, String objectName, String fileName) {
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
	private void deleteObject(Bucket bucket, String objectName) {
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
	private URL getDownloadURL(Bucket bucket, String fileName) {
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket.getName(), fileName);
		URL url;
		try {
			url = new URL("");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		url = connection.generatePresignedUrl(request);
		System.out.println(url);
		return url;
	}
}
