package com.onenow.salesforce;

import java.util.ArrayList;
import java.util.List;

import com.enremmeta.onenow.summit.Constants;

public class AccountListerImpl implements AccountLister {

	private List<CustomerAccount> customers;
	
	public AccountListerImpl() {
		customers = new ArrayList<CustomerAccount>();
		initAccountList();  // adds us to the list until connected to Salesforce
	}
	
	@Override
	public List<CustomerAccount> getAccountList() { // read from Salesforce
		return customers;
	}

	private void initAccountList() {
		CustomerAccount initialCustomer = new CustomerAccount(Constants.ONENOW);  
		customers.add(initialCustomer);
		AccountCloud initialCloud = new AccountCloud();
		initialCustomer.addCloud(initialCloud);
	}
	
	@Override
	public void setAccountList() { // write to Salesforce
	}
	
	public String toString() {
		String customerList = customers.toString();
		System.out.println("Customers:" + customerList);
		return customerList;
	}
	
}
