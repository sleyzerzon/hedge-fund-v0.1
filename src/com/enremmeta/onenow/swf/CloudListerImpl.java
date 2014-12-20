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
	public List<AccountCloud> getCloudList() {  // Connect to Salesforce: read list of clouds
		return clouds;
	}
	
	@Override
	public void setCloudList() { // Connect to Salesforce here: write list of clouds
		
	}
	
	public String toString() {
		String cloudList = clouds.toString();
		System.out.println("Clouds:" + cloudList);
		return cloudList;
	}
	
}
