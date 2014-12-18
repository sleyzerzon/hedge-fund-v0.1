package com.enremmeta.onenow.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.enremmeta.onenow.summit.Constants;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 10)
@Activities(version = Constants.AWS_SWF_VERSION)
public interface PriceFetcher {

}
