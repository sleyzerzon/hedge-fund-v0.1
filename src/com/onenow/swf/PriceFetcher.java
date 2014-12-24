package com.onenow.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.workflow.ConstantsWorkflow;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 10)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION)
public interface PriceFetcher {

}
