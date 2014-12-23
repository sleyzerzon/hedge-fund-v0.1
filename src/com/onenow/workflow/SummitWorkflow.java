package com.onenow.workflow;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;
import com.onenow.summit.Constants;

@Workflow
@WorkflowRegistrationOptions(defaultTaskStartToCloseTimeoutSeconds = 3600, defaultExecutionStartToCloseTimeoutSeconds = 3600, defaultTaskList = Constants.AWS_SWF_TASK_LIST_NAME)
public interface SummitWorkflow {

	@Execute(version = Constants.AWS_SWF_VERSION)
	public void mainFlow();
}
