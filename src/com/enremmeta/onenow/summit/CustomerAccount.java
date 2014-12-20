package com.enremmeta.onenow.summit;

import java.util.ArrayList;
import java.util.List;

public class CustomerAccount {

	private String account;	
	private List<AccountCloud> clouds;  // an acccount has clouds
	
	public CustomerAccount() {
		this.clouds = new ArrayList<AccountCloud>();
	}
	
	// Account
	public CustomerAccount(String name) {
		setName(name);
	}
	public void setName(String name) {
		this.account = name;
	}

	public String getName() {
		return this.account;
	}	
	
	// Cloud
	public List<AccountCloud> getCloudList() {
		return this.clouds;
	}
	public AccountCloud getCloud() {
		return this.clouds.get(0); // defaults to the first cloud
	}
	public void addCloud(AccountCloud cloud) {
		this.clouds.add(cloud);
	}
	public void setCloud(AccountCloud cloud) {
		addCloud(cloud);
	}

}
