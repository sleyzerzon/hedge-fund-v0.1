package com.enremmeta.onenow.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.enremmeta.onenow.summit.Constants;

public class YakActivitiesWorker {

	public YakActivitiesWorker() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		ClientConfiguration config = new ClientConfiguration()
				.withSocketTimeout(70 * 1000);

		AWSCredentials awsCredentials = new BasicAWSCredentials(
				Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_KEY);

		AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(
				awsCredentials, config);
		service.setEndpoint("https://swf.us-east-1.amazonaws.com");

		ActivityWorker aw = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		aw.addActivitiesImplementation(new CloudListerImpl());
		aw.start();
		System.out.println(aw.getIdentity());
	}

}
