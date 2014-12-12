package com.enremmeta.onenow.swf;

import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 10)
@SummitActivities(version = "1.0")
public interface ActivitiesIfc {

}
