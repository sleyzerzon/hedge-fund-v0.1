/**
 * This code was generated from {@link com.onenow.workflow.SummitWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.workflow;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClient;

public interface SummitWorkflowClient extends WorkflowClient
{
    Promise<Void> mainFlow();
    Promise<Void> mainFlow(Promise<?>... waitFor);
    Promise<Void> mainFlow(StartWorkflowOptions optionsOverride, Promise<?>... waitFor);
}