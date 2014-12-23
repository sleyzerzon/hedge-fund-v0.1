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

		ActivityWorker aw1 = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		aw1.addActivitiesImplementation(new CloudListerImpl());
		aw1.start();
		System.out.println(aw1.getIdentity());
		

		ActivityWorker aw2 = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		aw2.addActivitiesImplementation(new CloudPriceListerImpl());
		aw2.start();
		System.out.println(aw2.getIdentity());
	}

}
