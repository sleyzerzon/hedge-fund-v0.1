package com.onenow.admin;

import java.util.logging.Level;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.kinesis.AmazonKinesis;
import com.amazonaws.services.kinesis.AmazonKinesisClient;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.onenow.util.SampleUtils;
import com.onenow.util.Watchr;

public class InitAmazon {

	private static String s3Endpoint = "http://s3.amazonaws.com/";
	
	//	Set credentials in the AWS credentials profile file on your local system, located at:
	//	~/.aws/credentials on Linux, OS X, or Unix
	//	This file should contain lines in the following format:
	//	[default]
	//	aws_access_key_id = your_access_key_id
	//	aws_secret_access_key = your_secret_access_key
	//	More at: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-setup.html#java-dg-using-maven
	//  And at: http://docs.aws.amazon.com/AWSSdkDocsJava/latest/DeveloperGuide/credentials.html

	public InitAmazon() {
		
	}
	
	// CREDENTIALS
	public static DefaultAWSCredentialsProviderChain getProviderChain() {
		
		//		DefaultAWSCredentialsProviderChain looks for credentials in this order:
		//			1. Environment Variables - AWS_ACCESS_KEY_ID and AWS_SECRET_KEY
		//			2. Java System Properties - aws.accessKeyId and aws.secretKey
		//			3. Credential profiles file at the default location (~/.aws/credentials) shared by all AWS SDKs and the AWS CLI
		//			4. Instance Profile credentials delivered through the Amazon EC2 metadata service
		return new DefaultAWSCredentialsProviderChain();
	}
	
	public static AWSCredentials getCredentials() {
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider().getCredentials();
			} 
		catch (Exception e) {
			throw new AmazonClientException(
		"Cannot load the credentials from the credential profiles file. " +
		"Please make sure that your credentials file is at the correct " +
		"location (~/.aws/credentials), and is in valid format.",
		                    e);
		        }
		return credentials;
	}
	
	// S3
	public static AmazonS3 getS3Connection() {
		
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTP);
		
		AmazonS3 s3Client = null;
		try {
			if(NetworkConfig.isMac()) {
				s3Client = new AmazonS3Client();
			} else {
				s3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider());
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}		
		s3Client.setEndpoint(s3Endpoint);
		
		return s3Client;
	}
	
	// SQS
	public static AmazonSQS getSQS(Region region) {
		AWSCredentials credentials = InitAmazon.getCredentials();
		AmazonSQS sqs = new AmazonSQSClient(credentials);
		
		sqs.setRegion(region);
		
		return sqs;
	}

	// CLIENT 
	public static ClientConfiguration getClientConfig() {
		return SampleUtils.configureUserAgentForSample(new ClientConfiguration());
	}
	
	// KINESIS
	public static AmazonKinesis getKinesis(Region region) {

		AmazonKinesis kinesis = null;

		try {
			Watchr.log(Level.INFO, "Creating new Kinesis client: credentials");
			if(NetworkConfig.isMac()) {
				// AmazonS3 s3Client = new AmazonS3Client(new InstanceProfileCredentialsProvider());
				kinesis = new AmazonKinesisClient(new DefaultAWSCredentialsProviderChain(), getClientConfig());
			} else {
				boolean refreshCredentials = false;
				AWSCredentialsProvider credentialsProvider = new InstanceProfileCredentialsProvider(refreshCredentials);
				kinesis = new AmazonKinesisClient(credentialsProvider.getCredentials());
			}
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, "Error creating AmazonKinesisClient" );
			e.printStackTrace();
		}
        
        kinesis.setRegion(region);
        
        return kinesis;
	}
	
	// DYNAMO DB
	public static AmazonDynamoDB getDynamo(Region region) {
		
        AmazonDynamoDB dynamoDB = null;
		try {
			if(NetworkConfig.isMac()) {
				dynamoDB = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain(), getClientConfig());
			} else {
				dynamoDB = new AmazonDynamoDBClient(new InstanceProfileCredentialsProvider());
			}
			dynamoDB.setRegion(region);
		} catch (IllegalArgumentException e) {
			System.out.println("COULD NOT CREATE DYNAMODB CLIENT");
			e.printStackTrace();
		}
        
        return dynamoDB;
	}
	


}
