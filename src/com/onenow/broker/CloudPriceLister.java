package com.onenow.broker;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.summit.AwsPricing;
import com.onenow.workflow.ConstantsWorkflow;

@ActivityRegistrationOptions(	defaultTaskScheduleToStartTimeoutSeconds = 3600, 
								defaultTaskStartToCloseTimeoutSeconds = 3600, 
								defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION)
public interface CloudPriceLister {

	public abstract List<String> getInstanceTypes(String provider);

	public abstract List<String> getProducts(String provider);

	public abstract List<String> getRegions(String provider);

	public abstract AwsPricing onDemandPricing();
}
