/**
 * This code was generated from {@link com.onenow.investor.PurchaseWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.investor;

import com.amazonaws.services.simpleworkflow.flow.core.Promise;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClient;

public interface PurchaseWorkflowClient extends WorkflowClient
{
    Promise<Void> mainFlow();
    Promise<Void> mainFlow(Promise<?>... waitFor);
    Promise<Void> mainFlow(StartWorkflowOptions optionsOverride, Promise<?>... waitFor);
}