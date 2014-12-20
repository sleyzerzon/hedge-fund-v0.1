package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.enremmeta.onenow.summit.AccountCloud;

public class SummitWorkflowImpl implements SummitWorkflow {

	private CloudListerClient cloudLister = new CloudListerClientImpl();

	@Asynchronous
	void processCloudList(Promise<List<AccountCloud>> clouds) {
		System.out.println(clouds.toString());
	}

	@Override
	public void mainFlow() {
		Promise<List<AccountCloud>> clouds = cloudLister.getCloudList();
		processCloudList(clouds);
	}

}
