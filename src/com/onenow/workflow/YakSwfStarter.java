package com.onenow.workflow;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.onenow.summit.ConstantsSummit;

public class YakSwfStarter {

	public YakSwfStarter() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Exception {
		ClientConfiguration config = new ClientConfiguration()
				.withSocketTimeout(70 * 1000);

		AWSCredentials awsCredentials = new BasicAWSCredentials(
				ConstantsSummit.AWS_ACCESS_KEY, ConstantsSummit.AWS_SECRET_KEY);

		AmazonSimpleWorkflow service = new AmazonSimpleWorkflowClient(awsCredentials, config);
		service.setEndpoint("https://swf.us-east-1.amazonaws.com");
		// TODO start all workflows...
		
		// SummitWorkflowClientExternalFactory factory = new
		// SummitWorkflowClientExternalFactoryImpl(
		// service, ConstantsWorkflow.AWS_SWF_DOMAIN);
		// SummitWorkflowClientExternal client = factory.getClient();
		// client.mainFlow();
		PurchaseWorkflowClientExternalFactory factory = new PurchaseWorkflowClientExternalFactoryImpl(
				service, ConstantsWorkflow.AWS_SWF_DOMAIN);
		PurchaseWorkflowClientExternal client = factory.getClient();
		client.mainFlow();
		System.out.println("Execution ID: " + client.getWorkflowExecution().getRunId() + "; " + client.getWorkflowExecution().getWorkflowId());

	}
}
