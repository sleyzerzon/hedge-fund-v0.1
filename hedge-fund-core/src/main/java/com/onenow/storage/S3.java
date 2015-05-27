package com.onenow.storage;

import java.io.ByteArrayInputStream;
import java.io.File;
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

		List<Bucket> buckets = getBuckets();
	}

	private void setEndpoint() {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AmazonS3 conn = new AmazonS3Client(credentials);
		connection.setEndpoint("objects.dreamhost.com");
	}
	
	private AmazonS3 getConnection() {
	
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);

		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);

		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint("endpoint.com");
		
		return conn;

	}
	
	private List<Bucket> getBuckets() {
		List<Bucket> buckets = connection.listBuckets();
		for (Bucket bucket : buckets) {
		        System.out.println(bucket.getName() + "\t" +
		                StringUtils.fromDate(bucket.getCreationDate()));
		}
		return buckets;
	}
	
	private void createBucket(String name) {
		Bucket bucket = connection.createBucket("my-new-bucket");
	}
}
