package com.onenow.workflow;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.ActivityWorker;
import com.onenow.broker.BrokerActivityImpl;
import com.onenow.broker.CloudPriceListerImpl;
import com.onenow.summit.ConstantsSummit;

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
		// CloudPriceLister Activity
		ActivityWorker aw = new ActivityWorker(service,
				ConstantsWorkflow.AWS_SWF_DOMAIN,
				ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME);
		aw.addActivitiesImplementation(new BrokerActivityImpl());
//		aw.addActivitiesImplementation(new IBrokersActivityImpl());
		aw.start();
//		aw.addActivitiesImplementation(new SForceActivityImpl());
		System.out.println("*** ACTIVITY WORKER ID: " + aw.getIdentity());

	}

}
