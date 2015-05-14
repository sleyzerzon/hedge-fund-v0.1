package com.onenow.portfolio;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;
import com.onenow.constant.ConstantsWorkflow;

@Workflow
@WorkflowRegistrationOptions(	defaultTaskStartToCloseTimeoutSeconds = 300, 
								defaultExecutionStartToCloseTimeoutSeconds = 300, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
public interface PurchaseWorkflow {

	@Execute(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
	public void mainFlow();
}
