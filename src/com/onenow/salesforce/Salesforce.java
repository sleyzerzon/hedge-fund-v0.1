package com.onenow.salesforce;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.enremmeta.onenow.summit.Constants;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 3600, defaultTaskStartToCloseTimeoutSeconds = 3600, defaultTaskList = Constants.AWS_SWF_TASK_LIST_NAME)
@Activities(version = Constants.AWS_SWF_VERSION)
public interface Salesforce {
	@Activity
	public List<System__c> getSystem() throws ConnectionException;
	public List<Account> getAccounts() throws ConnectionException;
	public List<Cloud__c> getClouds() throws ConnectionException;
	public List<Reduction__c> getReductions() throws ConnectionException;
	public void create(SObject[] objects) throws ConnectionException;
//	public List<AccountCloud> getCloudList();
//	public void setCloudList();
//	public List<CustomerAccount> getAccountList();
//	public void setAccountList();
}
