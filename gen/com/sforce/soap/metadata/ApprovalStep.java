package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class ApprovalStep implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public ApprovalStep() {}

    /**
     * element : allowDelegate of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo allowDelegate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","allowDelegate","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean allowDelegate__is_set = false;

    private boolean allowDelegate;

    public boolean getAllowDelegate() {
      return allowDelegate;
    }

    public boolean isAllowDelegate() {
      return allowDelegate;
    }

    public void setAllowDelegate(boolean allowDelegate) {
      this.allowDelegate = allowDelegate;
      allowDelegate__is_set = true;
    }

    /**
     * element : approvalActions of type {http://soap.sforce.com/2006/04/metadata}ApprovalAction
     * java type: com.sforce.soap.metadata.ApprovalAction
     */
    private static final com.sforce.ws.bind.TypeInfo approvalActions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","approvalActions","http://soap.sforce.com/2006/04/metadata","ApprovalAction",0,1,true);

    private boolean approvalActions__is_set = false;

    private com.sforce.soap.metadata.ApprovalAction approvalActions;

    public com.sforce.soap.metadata.ApprovalAction getApprovalActions() {
      return approvalActions;
    }

    public void setApprovalActions(com.sforce.soap.metadata.ApprovalAction approvalActions) {
      this.approvalActions = approvalActions;
      approvalActions__is_set = true;
    }

    /**
     * element : assignedApprover of type {http://soap.sforce.com/2006/04/metadata}ApprovalStepApprover
     * java type: com.sforce.soap.metadata.ApprovalStepApprover
     */
    private static final com.sforce.ws.bind.TypeInfo assignedApprover__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","assignedApprover","http://soap.sforce.com/2006/04/metadata","ApprovalStepApprover",1,1,true);

    private boolean assignedApprover__is_set = false;

    private com.sforce.soap.metadata.ApprovalStepApprover assignedApprover;

    public com.sforce.soap.metadata.ApprovalStepApprover getAssignedApprover() {
      return assignedApprover;
    }

    public void setAssignedApprover(com.sforce.soap.metadata.ApprovalStepApprover assignedApprover) {
      this.assignedApprover = assignedApprover;
      assignedApprover__is_set = true;
    }

    /**
     * element : description of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo description__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","description","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean description__is_set = false;

    private java.lang.String description;

    public java.lang.String getDescription() {
      return description;
    }

    public void setDescription(java.lang.String description) {
      this.description = description;
      description__is_set = true;
    }

    /**
     * element : entryCriteria of type {http://soap.sforce.com/2006/04/metadata}ApprovalEntryCriteria
     * java type: com.sforce.soap.metadata.ApprovalEntryCriteria
     */
    private static final com.sforce.ws.bind.TypeInfo entryCriteria__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","entryCriteria","http://soap.sforce.com/2006/04/metadata","ApprovalEntryCriteria",0,1,true);

    private boolean entryCriteria__is_set = false;

    private com.sforce.soap.metadata.ApprovalEntryCriteria entryCriteria;

    public com.sforce.soap.metadata.ApprovalEntryCriteria getEntryCriteria() {
      return entryCriteria;
    }

    public void setEntryCriteria(com.sforce.soap.metadata.ApprovalEntryCriteria entryCriteria) {
      this.entryCriteria = entryCriteria;
      entryCriteria__is_set = true;
    }

    /**
     * element : ifCriteriaNotMet of type {http://soap.sforce.com/2006/04/metadata}StepCriteriaNotMetType
     * java type: com.sforce.soap.metadata.StepCriteriaNotMetType
     */
    private static final com.sforce.ws.bind.TypeInfo ifCriteriaNotMet__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","ifCriteriaNotMet","http://soap.sforce.com/2006/04/metadata","StepCriteriaNotMetType",0,1,true);

    private boolean ifCriteriaNotMet__is_set = false;

    private com.sforce.soap.metadata.StepCriteriaNotMetType ifCriteriaNotMet;

    public com.sforce.soap.metadata.StepCriteriaNotMetType getIfCriteriaNotMet() {
      return ifCriteriaNotMet;
    }

    public void setIfCriteriaNotMet(com.sforce.soap.metadata.StepCriteriaNotMetType ifCriteriaNotMet) {
      this.ifCriteriaNotMet = ifCriteriaNotMet;
      ifCriteriaNotMet__is_set = true;
    }

    /**
     * element : label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo label__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","label","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean label__is_set = false;

    private java.lang.String label;

    public java.lang.String getLabel() {
      return label;
    }

    public void setLabel(java.lang.String label) {
      this.label = label;
      label__is_set = true;
    }

    /**
     * element : name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo name__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","name","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean name__is_set = false;

    private java.lang.String name;

    public java.lang.String getName() {
      return name;
    }

    public void setName(java.lang.String name) {
      this.name = name;
      name__is_set = true;
    }

    /**
     * element : rejectBehavior of type {http://soap.sforce.com/2006/04/metadata}ApprovalStepRejectBehavior
     * java type: com.sforce.soap.metadata.ApprovalStepRejectBehavior
     */
    private static final com.sforce.ws.bind.TypeInfo rejectBehavior__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","rejectBehavior","http://soap.sforce.com/2006/04/metadata","ApprovalStepRejectBehavior",0,1,true);

    private boolean rejectBehavior__is_set = false;

    private com.sforce.soap.metadata.ApprovalStepRejectBehavior rejectBehavior;

    public com.sforce.soap.metadata.ApprovalStepRejectBehavior getRejectBehavior() {
      return rejectBehavior;
    }

    public void setRejectBehavior(com.sforce.soap.metadata.ApprovalStepRejectBehavior rejectBehavior) {
      this.rejectBehavior = rejectBehavior;
      rejectBehavior__is_set = true;
    }

    /**
     * element : rejectionActions of type {http://soap.sforce.com/2006/04/metadata}ApprovalAction
     * java type: com.sforce.soap.metadata.ApprovalAction
     */
    private static final com.sforce.ws.bind.TypeInfo rejectionActions__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","rejectionActions","http://soap.sforce.com/2006/04/metadata","ApprovalAction",0,1,true);

    private boolean rejectionActions__is_set = false;

    private com.sforce.soap.metadata.ApprovalAction rejectionActions;

    public com.sforce.soap.metadata.ApprovalAction getRejectionActions() {
      return rejectionActions;
    }

    public void setRejectionActions(com.sforce.soap.metadata.ApprovalAction rejectionActions) {
      this.rejectionActions = rejectionActions;
      rejectionActions__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       __typeMapper.writeBoolean(__out, allowDelegate__typeInfo, allowDelegate, allowDelegate__is_set);
       __typeMapper.writeObject(__out, approvalActions__typeInfo, approvalActions, approvalActions__is_set);
       __typeMapper.writeObject(__out, assignedApprover__typeInfo, assignedApprover, assignedApprover__is_set);
       __typeMapper.writeString(__out, description__typeInfo, description, description__is_set);
       __typeMapper.writeObject(__out, entryCriteria__typeInfo, entryCriteria, entryCriteria__is_set);
       __typeMapper.writeObject(__out, ifCriteriaNotMet__typeInfo, ifCriteriaNotMet, ifCriteriaNotMet__is_set);
       __typeMapper.writeString(__out, label__typeInfo, label, label__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeObject(__out, rejectBehavior__typeInfo, rejectBehavior, rejectBehavior__is_set);
       __typeMapper.writeObject(__out, rejectionActions__typeInfo, rejectionActions, rejectionActions__is_set);
    }

    @Override
    public void load(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
      __typeMapper.consumeStartTag(__in);
      loadFields(__in, __typeMapper);
      __typeMapper.consumeEndTag(__in);
    }

    protected void loadFields(com.sforce.ws.parser.XmlInputStream __in,
        com.sforce.ws.bind.TypeMapper __typeMapper) throws java.io.IOException, com.sforce.ws.ConnectionException {
        __in.peekTag();
        if (__typeMapper.isElement(__in, allowDelegate__typeInfo)) {
            setAllowDelegate(__typeMapper.readBoolean(__in, allowDelegate__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, approvalActions__typeInfo)) {
            setApprovalActions((com.sforce.soap.metadata.ApprovalAction)__typeMapper.readObject(__in, approvalActions__typeInfo, com.sforce.soap.metadata.ApprovalAction.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, assignedApprover__typeInfo)) {
            setAssignedApprover((com.sforce.soap.metadata.ApprovalStepApprover)__typeMapper.readObject(__in, assignedApprover__typeInfo, com.sforce.soap.metadata.ApprovalStepApprover.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, description__typeInfo)) {
            setDescription(__typeMapper.readString(__in, description__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, entryCriteria__typeInfo)) {
            setEntryCriteria((com.sforce.soap.metadata.ApprovalEntryCriteria)__typeMapper.readObject(__in, entryCriteria__typeInfo, com.sforce.soap.metadata.ApprovalEntryCriteria.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, ifCriteriaNotMet__typeInfo)) {
            setIfCriteriaNotMet((com.sforce.soap.metadata.StepCriteriaNotMetType)__typeMapper.readObject(__in, ifCriteriaNotMet__typeInfo, com.sforce.soap.metadata.StepCriteriaNotMetType.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, label__typeInfo)) {
            setLabel(__typeMapper.readString(__in, label__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, rejectBehavior__typeInfo)) {
            setRejectBehavior((com.sforce.soap.metadata.ApprovalStepRejectBehavior)__typeMapper.readObject(__in, rejectBehavior__typeInfo, com.sforce.soap.metadata.ApprovalStepRejectBehavior.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, rejectionActions__typeInfo)) {
            setRejectionActions((com.sforce.soap.metadata.ApprovalAction)__typeMapper.readObject(__in, rejectionActions__typeInfo, com.sforce.soap.metadata.ApprovalAction.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[ApprovalStep ");
      sb.append(" allowDelegate='").append(com.sforce.ws.util.Verbose.toString(allowDelegate)).append("'\n");
      sb.append(" approvalActions='").append(com.sforce.ws.util.Verbose.toString(approvalActions)).append("'\n");
      sb.append(" assignedApprover='").append(com.sforce.ws.util.Verbose.toString(assignedApprover)).append("'\n");
      sb.append(" description='").append(com.sforce.ws.util.Verbose.toString(description)).append("'\n");
      sb.append(" entryCriteria='").append(com.sforce.ws.util.Verbose.toString(entryCriteria)).append("'\n");
      sb.append(" ifCriteriaNotMet='").append(com.sforce.ws.util.Verbose.toString(ifCriteriaNotMet)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" rejectBehavior='").append(com.sforce.ws.util.Verbose.toString(rejectBehavior)).append("'\n");
      sb.append(" rejectionActions='").append(com.sforce.ws.util.Verbose.toString(rejectionActions)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
