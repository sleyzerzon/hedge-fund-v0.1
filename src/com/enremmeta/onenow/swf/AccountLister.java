package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

import com.enremmeta.onenow.summit.Constants;
import com.enremmeta.onenow.summit.CustomerAccount;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 3600, defaultTaskStartToCloseTimeoutSeconds = 3600, defaultTaskList = Constants.AWS_SWF_TASK_LIST_NAME)
@Activities(version = Constants.AWS_SWF_VERSION)
public interface AccountLister {
	@Activity
	public List<CustomerAccount> getAccountList();
	public void setAccountList();
	
}
