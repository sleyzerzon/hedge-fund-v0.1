package com.enremmeta.onenow.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.amazonaws.services.simpleworkflow.model.Run;
import com.amazonaws.services.simpleworkflow.model.StartWorkflowExecutionRequest;
import com.amazonaws.services.simpleworkflow.model.WorkflowType;
import com.enremmeta.onenow.summit.Constants;

public class YakSwfStarter {

	public YakSwfStarter() {
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
		StartWorkflowExecutionRequest swer = new StartWorkflowExecutionRequest()
				.withDomain(Constants.AWS_SWF_DOMAIN)
				.withWorkflowId("39068@Gregory-Golberg-Macbook-Air.local")
				.withWorkflowType(
						new WorkflowType().withName("SummitWorkflowIfc.mainFlow").withVersion(
								"5.0"));
		Run run = service.startWorkflowExecution(swer);
		System.out.println(run.getRunId());
	}
}
