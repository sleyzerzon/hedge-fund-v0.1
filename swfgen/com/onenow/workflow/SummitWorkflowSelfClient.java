/**
 * This code was generated from {@link com.onenow.workflow.SummitWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.workflow;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowSelfClient;
import com.onenow.aws.SummitWorkflow;

public interface SummitWorkflowSelfClient extends WorkflowSelfClient
{
    void mainFlow();
    void mainFlow(StartWorkflowOptions optionsOverride, Promise<?>... waitFor);
}