package com.enremmeta.onenow.swf;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.core.Settable;

public class SforceCloudLister implements CloudLister {

	public SforceCloudLister() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getCloudList() {
		List<String> clouds = new ArrayList<String>();
		clouds.add("aws");
		return clouds;
	}

}
