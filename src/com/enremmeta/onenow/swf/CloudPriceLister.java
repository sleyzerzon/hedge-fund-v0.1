package com.enremmeta.onenow.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.enremmeta.onenow.summit.AwsPricing;
import com.enremmeta.onenow.summit.Constants;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 3600, defaultTaskStartToCloseTimeoutSeconds = 3600, defaultTaskList = Constants.AWS_SWF_TASK_LIST_NAME)
@Activities(version = Constants.AWS_SWF_VERSION)
public interface CloudPriceLister {
	AwsPricing onDemandPricing();
}
