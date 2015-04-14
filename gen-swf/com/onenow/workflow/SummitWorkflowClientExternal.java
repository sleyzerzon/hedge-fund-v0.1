/**
 * This code was generated from {@link com.onenow.workflow.SummitWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.workflow;

import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClientExternal;
import com.onenow.aws.SummitWorkflow;

public interface SummitWorkflowClientExternal extends WorkflowClientExternal
{
    void mainFlow();
    void mainFlow(StartWorkflowOptions optionsOverride);
}