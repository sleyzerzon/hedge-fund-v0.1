package com.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.workflow.ConstantsWorkflow;

@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 3600, 
								defaultTaskStartToCloseTimeoutSeconds = 3600, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface OpenTsdbActivity {
	@Activity
	
	@Activity 
	public List<Integer> getPriceData(String cloud);
}
