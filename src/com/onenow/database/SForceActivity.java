package com.onenow.database;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.workflow.ConstantsWorkflow;
import com.sforce.soap.enterprise.sobject.Account;
import com.sforce.soap.enterprise.sobject.Cloud__c;
import com.sforce.soap.enterprise.sobject.Reduction__c;
import com.sforce.soap.enterprise.sobject.SObject;
import com.sforce.soap.enterprise.sobject.System__c;
import com.sforce.ws.ConnectionException;

@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 3600, 
								defaultTaskStartToCloseTimeoutSeconds = 3600, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface SForceActivity {
	@Activity
	public List<System__c> getSystem() throws ConnectionException;
	public List<Account> getAccounts() throws ConnectionException;
	public List<Cloud__c> getClouds() throws ConnectionException;
	public List<Reduction__c> getReductions() throws ConnectionException;
	public void create(SObject[] objects) throws ConnectionException;
}
