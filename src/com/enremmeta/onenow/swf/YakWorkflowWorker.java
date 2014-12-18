package com.enremmeta.onenow.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
import com.amazonaws.services.simpleworkflow.flow.WorkflowWorker;
import com.amazonaws.services.simpleworkflow.model.Run;
import com.amazonaws.services.simpleworkflow.model.StartWorkflowExecutionRequest;
import com.enremmeta.onenow.summit.Constants;

public class YakWorkflowWorker {

	public YakWorkflowWorker() {
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

		WorkflowWorker wfw = new WorkflowWorker(service,
				Constants.AWS_SWF_DOMAIN, Constants.AWS_SWF_TASK_LIST_NAME);
		wfw.addWorkflowImplementationType(SummitWorkflowImpl.class);
		wfw.start();
		System.out.println(wfw.getIdentity());


	}
}
