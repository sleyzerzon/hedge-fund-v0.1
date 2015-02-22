/**
 * This code was generated from {@link com.onenow.investor.PurchaseWorkflow}.
 *
 * Any changes made directly to this file will be lost when 
 * the code is regenerated.
 */
package com.onenow.investor;

import com.amazonaws.services.simpleworkflow.flow.DataConverter;
import com.amazonaws.services.simpleworkflow.flow.StartWorkflowOptions;
import com.amazonaws.services.simpleworkflow.flow.WorkflowClientExternalBase;
import com.amazonaws.services.simpleworkflow.flow.generic.GenericWorkflowClientExternal;
import com.amazonaws.services.simpleworkflow.model.WorkflowExecution;
import com.amazonaws.services.simpleworkflow.model.WorkflowType;

class PurchaseWorkflowClientExternalImpl extends WorkflowClientExternalBase implements PurchaseWorkflowClientExternal {

    public PurchaseWorkflowClientExternalImpl(WorkflowExecution workflowExecution, WorkflowType workflowType, 
            StartWorkflowOptions options, DataConverter dataConverter, GenericWorkflowClientExternal genericClient) {
        super(workflowExecution, workflowType, options, dataConverter, genericClient);
    }

    @Override
    public void mainFlow() { 
        mainFlow(null);
    }

    @Override
    public void mainFlow(StartWorkflowOptions startOptionsOverride) {
    
        Object[] _arguments_ = new Object[0]; 
        dynamicWorkflowClient.startWorkflowExecution(_arguments_, startOptionsOverride);
    }


}