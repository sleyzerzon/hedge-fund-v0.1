package com.enremmeta.onenow.swf;

import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.amazonaws.services.simpleworkflow.flow.annotations.Asynchronous;
import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.enremmeta.onenow.summit.Constants;


@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300,
                             defaultTaskStartToCloseTimeoutSeconds = 10,
                             defaultTaskList=Constants.AWS_SWF_TASK_LIST_NAME)
@Activities(version="5.0")
public interface CloudLister {
	@Asynchronous
	List<String> getCloudList();
}
