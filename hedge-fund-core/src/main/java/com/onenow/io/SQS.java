package com.onenow.io;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Level;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.onenow.admin.InitAmazon;
import com.onenow.util.Watchr;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * Fill in your AWS access credentials in the provided credentials file
 * template, and be sure to move the file to the default location
 * (~/.aws/credentials) where the sample code will load the credentials from.
 */
public class SQS {
	
	public SQS() {
		
	}
	
	public SQS(Regions regions) {
		/*
         * The ProfileCredentialsProvider will return your [default]
         * credential profile by reading from the credentials file located at
         * (~/.aws/credentials).
         */
	
	AmazonSQS sqs = InitAmazon.getSQS(Region.getRegion(regions));
	
	try {
		String myQueueUrl = createQueue(sqs);
		
		listQueues(sqs);
			
		sendMessage(sqs, myQueueUrl);
		            
		List<Message> messages = receiveMessages(sqs, myQueueUrl);
		
		deleteMesssage(sqs, myQueueUrl, messages);

        deleteQueue(sqs, myQueueUrl);
        
	} catch (AmazonServiceException ase) {
			catchAWSServiceException(ase);
        
	} catch (AmazonClientException ace) {
			catchAWSClientException(ace);
        }
    }

	private void catchAWSClientException(AmazonClientException ace) {
		String log = "Caught an AmazonClientException, which means the client encountered " +
		"a serious internal problem while trying to communicate with SQS, such as not " +
		"being able to access the network. " + ace.getMessage();
    	Watchr.log(Level.INFO, log);
	}

	private void catchAWSServiceException(AmazonServiceException ase) {
		String log = "Caught an AmazonServiceException, which means your request made it " +
		"to Amazon SQS, but was rejected with an error response for some reason." + "\n";
		log = log + "Error Message:    " + ase.getMessage();
		log = log + "HTTP Status Code: " + ase.getStatusCode();
		log = log + "AWS Error Code:   " + ase.getErrorCode();
		log = log + "Error Type:       " + ase.getErrorType();
		log = log + "Request ID:       " + ase.getRequestId();
    	Watchr.log(Level.INFO, log);
	}

	private void deleteQueue(AmazonSQS sqs, String myQueueUrl) {
		String log = "Deleting SQS queue: " + myQueueUrl;
    	Watchr.log(Level.INFO, log);
		sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
	}

	private void deleteMesssage(AmazonSQS sqs, String myQueueUrl,
			List<Message> messages) {
		String log = "Deleting a message: " + messages + " FROM " + myQueueUrl;
    	Watchr.log(Level.INFO, log);
		String messageRecieptHandle = messages.get(0).getReceiptHandle();
		sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageRecieptHandle));
	}

	private List<Message> receiveMessages(AmazonSQS sqs, String myQueueUrl) {
		
		String log = "Receiving messages from MyQueue: " + myQueueUrl;
    	Watchr.log(Level.INFO, log);
    	
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
		List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

		for (Message message : messages) {
			log = "  Message";
			log = log + "    MessageId:     " + message.getMessageId();
			log = log + "    ReceiptHandle: " + message.getReceiptHandle();
			log = log + "    MD5OfBody:     " + message.getMD5OfBody();
			log = log + "    Body:          " + message.getBody();
	    	Watchr.log(Level.INFO, log);

			for (Entry<String, String> entry : message.getAttributes().entrySet()) {
				log = "  Attribute";
				log = log + "    Name:  " + entry.getKey();
				log = log + "    Value: " + entry.getValue();
		    	Watchr.log(Level.INFO, log);
		        }
            }
		return messages;
	}

	private void sendMessage(AmazonSQS sqs, String myQueueUrl) {

		String log = "Sending a message to: " + myQueueUrl;
    	Watchr.log(Level.INFO, log);

		sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));
	}

	private void listQueues(AmazonSQS sqs) {

		String log = "Listing all queues in your account.";
    	Watchr.log(Level.INFO, log);

		for (String queueUrl : sqs.listQueues().getQueueUrls()) {
			log = "  QueueUrl: " + queueUrl;
	    	Watchr.log(Level.INFO, log);
            }
		System.out.println();
	}

	private String createQueue(AmazonSQS sqs) {
		
		String log = "Creating a new SQS queue called MyQueue.";
    	Watchr.log(Level.INFO, log);

		CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
		String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
		
		return myQueueUrl;
	}
}