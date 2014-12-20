package com.enremmeta.onenow.swf;

import java.util.ArrayList;
import java.util.List;

import com.enremmeta.onenow.summit.AccountCloud;

public class CloudListerImpl implements CloudLister {

	List<AccountCloud> clouds;
		
	public CloudListerImpl() {
		clouds = new ArrayList<AccountCloud>();
	}
	
	@Override
	public List<AccountCloud> getCloudList() {
		setCloudList();
		return clouds;
	}
	
	@Override
	public void setCloudList() { // Connect to Salesforce here: get list of clouds
		
	}
	
	public String toString() {
		String cloudList = clouds.toString();
		System.out.println("Clouds:" + cloudList);
		return cloudList;
	}
	

//	@Override
//	public List<Integer> getPriceData(String cloud) {
//		return Arrays.asList(new Integer[] {1,2,3,4});
//	}

	
}
