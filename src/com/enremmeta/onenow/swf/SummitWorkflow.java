package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class SummitWorkflow implements SummitWorkflowIfc {

	public SummitWorkflow() {
		// TODO Auto-generated constructor stub
	}

	public void fetchPrices() {

	}

	private CloudListerClient cloudLister = new CloudListerClientImpl();

	@Override
	public void mainFlow() {
		Promise<List<String>> clouds = cloudLister.getCloudList();
		System.out.println(clouds);
	}
}
