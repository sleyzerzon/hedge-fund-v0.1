package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;

public class SummitWorkflowImpl implements SummitWorkflow {

	private CloudListerClient cloudLister = new CloudListerClientImpl();

	private SummitWorkflowSelfClient selfClient = new SummitWorkflowSelfClientImpl();

	private int counter = 0;

	@Asynchronous
	void processCloudList(Promise<List<String>> clouds) {
		System.out.println(clouds.get());

	}

	public void processCloudList2(List<String> clouds) {
		System.out.println(clouds);
	}

	@Override
	public void mainFlow() {
		Promise<List<String>> clouds = cloudLister.getCloudList();
		processCloudList(clouds);
	}

	@Asynchronous
	private void printCloudList(Promise<List<String>> clouds) {
		System.out.println("Entering printCloudList()");
		if (clouds.isReady()) {
			System.out.println(clouds.get());
		}
		System.out.println("Exiting printCloudList()");
	}
}
