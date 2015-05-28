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
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
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
	
	private static String endpoint = "http://s3.amazonaws.com/";
	
	//	Set credentials in the AWS credentials profile file on your local system, located at:
	//	~/.aws/credentials on Linux, OS X, or Unix
	//	This file should contain lines in the following format:
	//	[default]
	//	aws_access_key_id = your_access_key_id
	//	aws_secret_access_key = your_secret_access_key
	//	More at: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-setup.html#java-dg-using-maven
	//  And at: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html
	private static AmazonS3 connection = getConnection();
	
	public S3 () {

		List<Bucket> buckets = listBuckets();
		
		System.out.println(buckets.toString());
	}
	
	private static AmazonS3 getConnection() {
	

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		//		DefaultAWSCredentialsProviderChain looks for credentials in this order:
		//			1. Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY
		//			2. Java System Properties - aws.accessKeyId and aws.secretKey
		//			3. Credential profiles file at the default location (~/.aws/credentials) shared by all AWS SDKs and the AWS CLI
		//			4. Instance profile credentials delivered through the Amazon EC2 metadata service
		AmazonS3 s3Client = new AmazonS3Client(new DefaultAWSCredentialsProviderChain());
		
		s3Client.setEndpoint(endpoint);
		
		return s3Client;
	}


	/** 
	 * Output example:
	 * 		mahbuckat1   2011-04-21T18:05:39.000Z
	 * 		mahbuckat2   2011-04-21T18:05:48.000Z
	 * 		mahbuckat3   2011-04-21T18:07:18.000Z
	 * @return
	 */
	private static List<Bucket> listBuckets() {
		List<Bucket> buckets = connection.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
		return buckets;
	}
	
	private static void createBucket(String name) {
		Bucket bucket = connection.createBucket(name);
	}
	
	/**
	 * Output example: 
	 * 		myphoto1.jpg 251262  2011-08-08T21:35:48.000Z
	 * 		myphoto2.jpg 262518  2011-08-08T21:38:01.000Z
	 * @param bucket
	 */
	private static void listObjects(Bucket bucket) {
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
	
	private static void deleteBucket(Bucket bucket) {
		connection.deleteBucket(bucket.getName());
	}
	
	/**
	 * This creates an object (i.e. hello.txt) with a content string (i.e. "Hello World!")
	 * @param bucket
	 * @param content
	 * @param objectName
	 */
	private static void createObject(Bucket bucket, String content, String objectName) {
		ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes());
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
		System.out.println(url);
		return url;
	}

}
