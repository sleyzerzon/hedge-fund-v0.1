package com.onenow.workflow;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.onenow.broker.CloudPriceListerImpl;
import com.onenow.broker.IBrokersActivityImpl;
import com.onenow.database.SForceActivityImpl;
import com.onenow.summit.ConstantsSummit;
import com.onenow.workflow.ConstantsWorkflow;

public class DaemonActivity {

	public DaemonActivity() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		
		ClientConfiguration config = new ClientConfiguration()
				.withSocketTimeout(70 * 1000);

		AWSCredentials awsCredentials = new BasicAWSCredentials(
				ConstantsSummit.AWS_ACCESS_KEY, ConstantsSummit.AWS_SECRET_KEY);

		// SWF Client
		AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(
				awsCredentials, config);
		service.setEndpoint("https://swf.us-east-1.amazonaws.com");

		// Salesforce Activity 
		ActivityWorker SForce = new ActivityWorker(service,
				ConstantsWorkflow.AWS_SWF_DOMAIN, ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME);
		SForce.addActivitiesImplementation(new SForceActivityImpl());
		SForce.start();
		System.out.println("*** ACTIVITY: " + "SALESFORCE " + SForce.getIdentity());
		
		// CloudPriceLister Activity
		ActivityWorker cloudPriceLister = new ActivityWorker(service,
				ConstantsWorkflow.AWS_SWF_DOMAIN, ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME);
		cloudPriceLister.addActivitiesImplementation(new CloudPriceListerImpl());
		cloudPriceLister.start();
		System.out.println("*** ACTIVITY: " + "CLOUDPRICELISTER " + cloudPriceLister.getIdentity());
		
		// IBrokers Activity
		ActivityWorker IBrokers = new ActivityWorker(service,
				ConstantsWorkflow.AWS_SWF_DOMAIN, ConstantsWorkflow.AWS_SWF_TASK_LIST2_NAME);
		IBrokers.addActivitiesImplementation(new IBrokersActivityImpl());
		IBrokers.start();
		System.out.println("*** ACTIVITY: " + "IBROKERS " + IBrokers.getIdentity());
	}

}
