package com.enremmeta.onenow.swf;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflow;
import com.amazonaws.services.simpleworkflow.AmazonSimpleWorkflowClient;
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
		SummitWorkflowClientExternalFactory factory = new SummitWorkflowClientExternalFactoryImpl(
				service, Constants.AWS_SWF_DOMAIN);
		SummitWorkflowClientExternal client = factory.getClient();
		client.mainFlow();

	}
}
