package com.onenow.io;

import java.util.ArrayList;
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
import com.onenow.admin.NetworkConfig;
import com.onenow.constant.DBname;
import com.onenow.constant.QueueName;
import com.onenow.util.Watchr;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 * Fill in your AWS access credentials in the provided credentials file
 * template, and be sure to move the file to the default location
 * (~/.aws/credentials) where the sample code will load the credentials from.
 */
public class SQS {
	
	static AmazonSQS sqs = InitAmazon.getSQS();
	
	public SQS() {
		
	}
	
	public SQS(Regions regions) {
		
    }

	/** 
	 * Each environment has its own queue
	 * @return
	 */
	public static String getHistoryQueueURL() {
		
		String queueURL = "https://sqs.us-east-1.amazonaws.com/355035832413/" + QueueName.HISTORY_DEVELOPMENT.toString();

		if(!NetworkConfig.isMac()) {
			
			queueURL = "https://sqs.us-east-1.amazonaws.com/355035832413/" + QueueName.HISTORY_STAGING.toString();
			
//			queueURL = "https://sqs.us-east-1.amazonaws.com/355035832413/" + QueueName.HISTORY_PRODUCTION.toString();
		} 
		return queueURL;

		
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

	public void deleteQueue(String myQueueUrl) {
		String log = "Deleting SQS queue: " + myQueueUrl;
    	Watchr.log(Level.INFO, log);
		try {
			sqs.deleteQueue(new DeleteQueueRequest(myQueueUrl));
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
	}

	public static void deleteMesssage(String myQueueUrl, List<Message> messages) {
		
		// String log = "Deleting a message list: " + messages + " FROM " + myQueueUrl;
    	// Watchr.log(Level.INFO, log);
		try {
			String messageRecieptHandle = messages.get(0).getReceiptHandle();
			sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageRecieptHandle));
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public static List<Message> receiveMessages(String myQueueUrl) {
		
		String log = "Receiving messages to " + myQueueUrl;
		// Watchr.log(Level.INFO, "Receiving messages to " + myQueueUrl);
    	
		ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
		List<Message> messages = new ArrayList<Message>();
		try {
			messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}

		for (Message message : messages) {
			log = "  Message";
			log = log + "    MessageId:     " + message.getMessageId();
			log = log + "    ReceiptHandle: " + message.getReceiptHandle();
			log = log + "    MD5OfBody:     " + message.getMD5OfBody();
			log = log + "    Body:          " + message.getBody();
			// Watchr.log(Level.INFO, log);

			for (Entry<String, String> entry : message.getAttributes().entrySet()) {
				log = "  Attribute";
				log = log + "    Name:  " + entry.getKey();
				log = log + "    Value: " + entry.getValue();
				// Watchr.log(Level.INFO, log);
		        }
            }
		return messages;
	}

	public void sendMessage(String message, String queueURL) {

		String log = "Sending " + message +  " to " + queueURL ;
    	Watchr.log(Level.INFO, log);

		try {
			sqs.sendMessage(new SendMessageRequest(queueURL, message));
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
	}

	public static void listQueues() {

		String log = "Listing all queues in your account.";
    	Watchr.log(Level.INFO, log);

		try {
			for (String queueUrl : sqs.listQueues().getQueueUrls()) {
				log = "-- Listed Queue URL: " + queueUrl;
				Watchr.log(Level.INFO, log);
			    }
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
	}

	public static String createQueue(QueueName name) {
		
		String log = "Creating a new SQS queue called MyQueue.";
    	Watchr.log(Level.INFO, log);

		CreateQueueRequest createQueueRequest = new CreateQueueRequest(name.toString());
		String myQueueUrl = "";
		try {
			myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
		} catch (Exception e) {
			Watchr.log(Level.SEVERE, e.toString());
		}
		
		return myQueueUrl;
	}
}