package com.onenow.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.onenow.constant.ConstantsWorkflow;
import com.onenow.portfolio.PurchaseWorkflowImpl;

public class DaemonWorkflow {

	public DaemonWorkflow() {
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

		// SummitWorkflowImpl
		WorkflowWorker ww = new WorkflowWorker(service,
				ConstantsWorkflow.AWS_SWF_DOMAIN,
				ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME);
		ww.addWorkflowImplementationType(SummitWorkflowImpl.class);
		ww.addWorkflowImplementationType(PurchaseWorkflowImpl.class);
		ww.start();
		System.out.println("*** WORKFLOW IDENTITY: " + "SUMMIT "
				+ ww.getIdentity());

	}
}
