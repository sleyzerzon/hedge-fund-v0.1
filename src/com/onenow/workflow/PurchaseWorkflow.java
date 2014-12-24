package com.onenow.workflow;

import com.amazonaws.services.simpleworkflow.flow.annotations.Execute;
import com.amazonaws.services.simpleworkflow.flow.annotations.Workflow;
import com.amazonaws.services.simpleworkflow.flow.annotations.WorkflowRegistrationOptions;
import com.onenow.workflow.ConstantsWorkflow;

@Workflow
@WorkflowRegistrationOptions(	defaultTaskStartToCloseTimeoutSeconds = 3600, 
								defaultExecutionStartToCloseTimeoutSeconds = 3600, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST2_NAME)
public interface PurchaseWorkflow {

	@Execute(version = ConstantsWorkflow.AWS_SWF_VERSION)
	public void mainFlow();
}
