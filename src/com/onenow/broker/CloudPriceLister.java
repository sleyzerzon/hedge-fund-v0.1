package com.onenow.broker;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.summit.AwsPricing;
import com.onenow.summit.Constants;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 3600, defaultTaskStartToCloseTimeoutSeconds = 3600, defaultTaskList = Constants.AWS_SWF_TASK_LIST_NAME)
@Activities(version = Constants.AWS_SWF_VERSION)
public interface CloudPriceLister {

	public abstract List<String> getInstanceTypes(String provider);

	public abstract List<String> getProducts(String provider);

	public abstract List<String> getRegions(String provider);
	
	List<String> getAvailableTransactionTypes(String provider);
	
	float getPrice(String provider, String xactnType, )

	public abstract AwsPricing onDemandPricing();
}
