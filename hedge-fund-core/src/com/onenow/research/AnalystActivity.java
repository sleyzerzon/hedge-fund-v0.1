package com.onenow.research;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.simpleworkflow.flow.annotations.Activities;
import com.amazonaws.services.simpleworkflow.flow.annotations.Activity;
import com.amazonaws.services.simpleworkflow.flow.annotations.ActivityRegistrationOptions;
import com.onenow.constant.AggregateFunction;
import com.onenow.constant.ConstantsWorkflow;
import com.onenow.constant.InvType;
import com.onenow.instrument.Investment;
import com.onenow.instrument.Underlying;
import com.onenow.portfolio.Trade;
import com.onenow.portfolio.Transaction;

@ActivityRegistrationOptions(defaultTaskScheduleToStartTimeoutSeconds = 300, defaultTaskStartToCloseTimeoutSeconds = 300, defaultTaskList = ConstantsWorkflow.AWS_SWF_TASK_LIST_NAME)
@Activities(version = ConstantsWorkflow.AWS_SWF_VERSION_DEV)
public interface AnalystActivity {

	List<Tick> getTimeSeries(String cloud, String region, String zone,
			String os, String instance, String source, InvType invType,
			AggregateFunction agg, AggregateFunction downsample);
}