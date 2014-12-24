package com.onenow.workflow;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.onenow.broker.CloudPriceListerImpl;
import com.onenow.salesforce.CloudListerImpl;
import com.onenow.salesforce.SForceActivityImpl;
import com.onenow.summit.Constants;

public class DaemonActivity {

	public DaemonActivity() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		
		ClientConfiguration config = new ClientConfiguration()
				.withSocketTimeout(70 * 1000);

		AWSCredentials awsCredentials = new BasicAWSCredentials(
				Constants.AWS_ACCESS_KEY, Constants.AWS_SECRET_KEY);

		// SWF Client
		AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(
				awsCredentials, config);
		service.setEndpoint("https://swf.us-east-1.amazonaws.com");

		// Salesforce Activity 
		ActivityWorker SForce = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		SForce.addActivitiesImplementation(new SForceActivityImpl());
		SForce.start();
		System.out.println(SForce.getIdentity());
		
		// CloudPriceLister Activity
		ActivityWorker cloudPriceLister = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		cloudPriceLister.addActivitiesImplementation(new CloudPriceListerImpl());
		cloudPriceLister.start();
		System.out.println(cloudPriceLister.getIdentity());
		
		// IBrokers Activity
		ActivityWorker IBrokers = new ActivityWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		IBrokers.addActivitiesImplementation(new CloudPriceListerImpl());
		IBrokers.start();
		System.out.println(IBrokers.getIdentity());
	}

}
