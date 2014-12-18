package com.enremmeta.onenow.swf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CloudListerImpl implements CloudLister {

	public CloudListerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<String> getCloudList() {
		System.out.println("Entering getCLoudList()");
		List<String> clouds = new ArrayList<String>();
		clouds.add("aws");
		System.out.println("Returning from getCloudList(): " + clouds);
		return clouds;
	}

	@Override
	public List<Integer> getPriceData(String cloud) {
		return Arrays.asList(new Integer[] {1,2,3,4});
	}

	
}
