package com.onenow.database;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.workflow.ConstantsWorkflow;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Day__c;
import com.sforce.soap.enterprise.sobject.Log__c;
import com.sforce.soap.enterprise.sobject.Market__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;

@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 3600, 
								defaultTaskStartToCloseTimeoutSeconds = 3600, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface DatabaseSystemActivity {
	@Activity
	public List<Reduction__c> getReductions() throws ConnectionException;
	public Reduction__c newRedution(String name, String mode, Double target) throws ConnectionException;
	public List<Day__c> getDays() throws ConnectionException;
	public Day__c[] newDay(Date date, Double duration, int count, Double ondemandRate, Double spotRate, Double spent);
	public List<Market__c> getMarkets() throws ConnectionException;
	public List<Cloud__c> getClouds() throws ConnectionException;
	public System__c getSystemTSDB() throws ConnectionException;
	public System__c getSystemSWF() throws ConnectionException;
	public System__c getSystemPricing() throws ConnectionException;
	public Log__c[] newLog(String source, String kind, String desc);	
	public List<Account> getAccounts() throws ConnectionException;
	public Market__c[] newMarket(String cloud, String instanceType, String operatingSystem, 
		   String pricingModel, String reduction, String region, String zone) throws ConnectionException;	
}
