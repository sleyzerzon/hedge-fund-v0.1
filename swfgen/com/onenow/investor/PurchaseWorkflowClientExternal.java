/**
 * This code was generated from {@link com.onenow.investor.PurchaseWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.investor;

import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClientExternal;

public interface PurchaseWorkflowClientExternal extends WorkflowClientExternal
{
    void mainFlow();
    void mainFlow(StartWorkflowOptions optionsOverride);
}