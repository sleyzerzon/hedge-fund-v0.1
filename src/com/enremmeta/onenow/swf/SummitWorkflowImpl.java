package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.enremmeta.onenow.summit.AwsPricing;
import com.enremmeta.onenow.summit.AccountCloud;

public class SummitWorkflowImpl implements SummitWorkflow {

	private CloudListerClient cloudLister = new CloudListerClientImpl();
	private CloudPriceListerClient cloudPriceLister = new CloudPriceListerClientImpl();

	private SummitWorkflowSelfClient selfClient = new SummitWorkflowSelfClientImpl();

	private int counter = 0;

	@Asynchronous
	void processCloudList(Promise<AwsPricing> awsPricing) {
		System.out.println(awsPricing);
	}

	@Override
	public void mainFlow() {
		Promise<AwsPricing> awsPricing = cloudPriceLister.onDemandPricing();
	}

}
