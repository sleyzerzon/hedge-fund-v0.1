package com.onenow.io;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.amazonaws.services.sqs.model.Message;
import com.onenow.data.EventRequestHistory;
import com.onenow.util.Serializer;
import com.onenow.util.TimeParser;

public class SQSTest {

	private static SQS sqs = new SQS();
	private static String queueURL = "https://sqs.us-east-1.amazonaws.com/355035832413/HISTORY_DEVELOPMENT";

	@Test
	public void sendMessage() {
		
		String requestSent = "Say hello to world!";

		// Send SQS request to broker
		String sentMessage = Serializer.serialize((Object) requestSent);
		sqs.sendMessage(sentMessage, queueURL);
		
		TimeParser.wait(5);
  
		while(true) {
			List<Message> serializedMessages = sqs.receiveMessages(queueURL);
			if(serializedMessages.size()>0) {	
				for(Message receivedMessage: serializedMessages) {
					Object requestObject = Serializer.deserialize(receivedMessage.getBody(), String.class);
					if(requestObject!=null) {
						String requestReceived = (String) requestObject;
						boolean success = Serializer.serialize(requestSent).equals(Serializer.serialize(requestReceived));
						if(success) {
							Assert.assertTrue(success);
							return;
					  }
				  }
			  }
		  }
		}
	}
}
