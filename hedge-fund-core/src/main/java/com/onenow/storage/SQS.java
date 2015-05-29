package com.onenow.storage;

import java.util.List;
import java.util.Map.Entry;

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
		System.out.println("Caught an AmazonClientException, which means the client encountered " +
		"a serious internal problem while trying to communicate with SQS, such as not " +
		"being able to access the network.");
		System.out.println("Error Message: " + ace.getMessage());
	}

	private void catchAWSServiceException(AmazonServiceException ase) {
		System.out.println("Caught an AmazonServiceException, which means your request made it " +
		"to Amazon SQS, but was rejected with an error response for some reason.");
		System.out.println("Error Message:    " + ase.getMessage());
		System.out.println("HTTP Status Code: " + ase.getStatusCode());
		System.out.println("AWS Error Code:   " + ase.getErrorCode());
		System.out.println("Error Type:       " + ase.getErrorType());
		System.out.println("Request ID:       " + ase.getRequestId());
	}

	private void deleteQueue(AmazonSQS sqs, String myQueueUrl) {
		// Delete a queue
		System.out.println("Deleting the test queue.\n");
		sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
	}

	private void deleteMesssage(AmazonSQS sqs, String myQueueUrl,
			List<Message> messages) {
		// Delete a message
		System.out.println("Deleting a message.\n");
		String messageRecieptHandle = messages.get(0).getReceiptHandle();
		sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageRecieptHandle));
	}

	private List<Message> receiveMessages(AmazonSQS sqs, String myQueueUrl) {
		// Receive messages
		System.out.println("Receiving messages from MyQueue.\n");
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
		List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();

		for (Message message : messages) {
			System.out.println("  Message");
			System.out.println("    MessageId:     " + message.getMessageId());
			System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
			System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
			System.out.println("    Body:          " + message.getBody());

			for (Entry<String, String> entry : message.getAttributes().entrySet()) {
				System.out.println("  Attribute");
				System.out.println("    Name:  " + entry.getKey());
				System.out.println("    Value: " + entry.getValue());
		        }
            }
		
			System.out.println();
		return messages;
	}

	private void sendMessage(AmazonSQS sqs, String myQueueUrl) {
		// Send a message
		System.out.println("Sending a message to MyQueue.\n");
		            sqs.sendMessage(new SendMessageRequest(myQueueUrl, "This is my message text."));
	}

	private void listQueues(AmazonSQS sqs) {
		// List queues
		System.out.println("Listing all queues in your account.\n");
		for (String queueUrl : sqs.listQueues().getQueueUrls()) {
			System.out.println("  QueueUrl: " + queueUrl);
            }
		System.out.println();
	}

	private String createQueue(AmazonSQS sqs) {
		// Create a queue
		System.out.println("Creating a new SQS queue called MyQueue.\n");
		CreateQueueRequest createQueueRequest = new CreateQueueRequest("MyQueue");
		String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
		return myQueueUrl;
	}
}