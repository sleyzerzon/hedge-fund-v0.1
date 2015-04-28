package com.sforce.soap.enterprise.sobject;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class WorkPerformanceCycle extends com.sforce.soap.enterprise.sobject.SObject {

    /**
     * Constructor
     */
    public WorkPerformanceCycle() {}

    /**
     * element : ActivityFrom of type {http://www.w3.org/2001/XMLSchema}date
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo ActivityFrom__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","ActivityFrom","http://www.w3.org/2001/XMLSchema","date",0,1,true);

    private boolean ActivityFrom__is_set = false;

    private java.util.Calendar ActivityFrom;

    public java.util.Calendar getActivityFrom() {
      return ActivityFrom;
    }

    public void setActivityFrom(java.util.Calendar ActivityFrom) {
      this.ActivityFrom = ActivityFrom;
      ActivityFrom__is_set = true;
    }

    /**
     * element : ActivityTo of type {http://www.w3.org/2001/XMLSchema}date
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo ActivityTo__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","ActivityTo","http://www.w3.org/2001/XMLSchema","date",0,1,true);

    private boolean ActivityTo__is_set = false;

    private java.util.Calendar ActivityTo;

    public java.util.Calendar getActivityTo() {
      return ActivityTo;
    }

    public void setActivityTo(java.util.Calendar ActivityTo) {
      this.ActivityTo = ActivityTo;
      ActivityTo__is_set = true;
    }

    /**
     * element : AttachedContentDocuments of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo AttachedContentDocuments__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","AttachedContentDocuments","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean AttachedContentDocuments__is_set = false;

    private com.sforce.soap.enterprise.QueryResult AttachedContentDocuments;

    public com.sforce.soap.enterprise.QueryResult getAttachedContentDocuments() {
      return AttachedContentDocuments;
    }

    public void setAttachedContentDocuments(com.sforce.soap.enterprise.QueryResult AttachedContentDocuments) {
      this.AttachedContentDocuments = AttachedContentDocuments;
      AttachedContentDocuments__is_set = true;
    }

    /**
     * element : CombinedAttachments of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo CombinedAttachments__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","CombinedAttachments","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean CombinedAttachments__is_set = false;

    private com.sforce.soap.enterprise.QueryResult CombinedAttachments;

    public com.sforce.soap.enterprise.QueryResult getCombinedAttachments() {
      return CombinedAttachments;
    }

    public void setCombinedAttachments(com.sforce.soap.enterprise.QueryResult CombinedAttachments) {
      this.CombinedAttachments = CombinedAttachments;
      CombinedAttachments__is_set = true;
    }

    /**
     * element : CreatedBy of type {urn:sobject.enterprise.soap.sforce.com}User
     * java type: com.sforce.soap.enterprise.sobject.User
     */
    private static final com.sforce.ws.bind.TypeInfo CreatedBy__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","CreatedBy","urn:sobject.enterprise.soap.sforce.com","User",0,1,true);

    private boolean CreatedBy__is_set = false;

    private com.sforce.soap.enterprise.sobject.User CreatedBy;

    public com.sforce.soap.enterprise.sobject.User getCreatedBy() {
      return CreatedBy;
    }

    public void setCreatedBy(com.sforce.soap.enterprise.sobject.User CreatedBy) {
      this.CreatedBy = CreatedBy;
      CreatedBy__is_set = true;
    }

    /**
     * element : CreatedById of type {urn:enterprise.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo CreatedById__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","CreatedById","urn:enterprise.soap.sforce.com","ID",0,1,true);

    private boolean CreatedById__is_set = false;

    private java.lang.String CreatedById;

    public java.lang.String getCreatedById() {
      return CreatedById;
    }

    public void setCreatedById(java.lang.String CreatedById) {
      this.CreatedById = CreatedById;
      CreatedById__is_set = true;
    }

    /**
     * element : CreatedDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo CreatedDate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","CreatedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true);

    private boolean CreatedDate__is_set = false;

    private java.util.Calendar CreatedDate;

    public java.util.Calendar getCreatedDate() {
      return CreatedDate;
    }

    public void setCreatedDate(java.util.Calendar CreatedDate) {
      this.CreatedDate = CreatedDate;
      CreatedDate__is_set = true;
    }

    /**
     * element : CurrentTask of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo CurrentTask__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","CurrentTask","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean CurrentTask__is_set = false;

    private java.lang.String CurrentTask;

    public java.lang.String getCurrentTask() {
      return CurrentTask;
    }

    public void setCurrentTask(java.lang.String CurrentTask) {
      this.CurrentTask = CurrentTask;
      CurrentTask__is_set = true;
    }

    /**
     * element : FeedSubscriptionsForEntity of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo FeedSubscriptionsForEntity__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","FeedSubscriptionsForEntity","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean FeedSubscriptionsForEntity__is_set = false;

    private com.sforce.soap.enterprise.QueryResult FeedSubscriptionsForEntity;

    public com.sforce.soap.enterprise.QueryResult getFeedSubscriptionsForEntity() {
      return FeedSubscriptionsForEntity;
    }

    public void setFeedSubscriptionsForEntity(com.sforce.soap.enterprise.QueryResult FeedSubscriptionsForEntity) {
      this.FeedSubscriptionsForEntity = FeedSubscriptionsForEntity;
      FeedSubscriptionsForEntity__is_set = true;
    }

    /**
     * element : FeedbackQuestionSets of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo FeedbackQuestionSets__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","FeedbackQuestionSets","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean FeedbackQuestionSets__is_set = false;

    private com.sforce.soap.enterprise.QueryResult FeedbackQuestionSets;

    public com.sforce.soap.enterprise.QueryResult getFeedbackQuestionSets() {
      return FeedbackQuestionSets;
    }

    public void setFeedbackQuestionSets(com.sforce.soap.enterprise.QueryResult FeedbackQuestionSets) {
      this.FeedbackQuestionSets = FeedbackQuestionSets;
      FeedbackQuestionSets__is_set = true;
    }

    /**
     * element : Feeds of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo Feeds__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","Feeds","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean Feeds__is_set = false;

    private com.sforce.soap.enterprise.QueryResult Feeds;

    public com.sforce.soap.enterprise.QueryResult getFeeds() {
      return Feeds;
    }

    public void setFeeds(com.sforce.soap.enterprise.QueryResult Feeds) {
      this.Feeds = Feeds;
      Feeds__is_set = true;
    }

    /**
     * element : Histories of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo Histories__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","Histories","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean Histories__is_set = false;

    private com.sforce.soap.enterprise.QueryResult Histories;

    public com.sforce.soap.enterprise.QueryResult getHistories() {
      return Histories;
    }

    public void setHistories(com.sforce.soap.enterprise.QueryResult Histories) {
      this.Histories = Histories;
      Histories__is_set = true;
    }

    /**
     * element : IsDeleted of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: java.lang.Boolean
     */
    private static final com.sforce.ws.bind.TypeInfo IsDeleted__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","IsDeleted","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean IsDeleted__is_set = false;

    private java.lang.Boolean IsDeleted;

    public java.lang.Boolean getIsDeleted() {
      return IsDeleted;
    }

    public void setIsDeleted(java.lang.Boolean IsDeleted) {
      this.IsDeleted = IsDeleted;
      IsDeleted__is_set = true;
    }

    /**
     * element : LastManagerRequestsSharedDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo LastManagerRequestsSharedDate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","LastManagerRequestsSharedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true);

    private boolean LastManagerRequestsSharedDate__is_set = false;

    private java.util.Calendar LastManagerRequestsSharedDate;

    public java.util.Calendar getLastManagerRequestsSharedDate() {
      return LastManagerRequestsSharedDate;
    }

    public void setLastManagerRequestsSharedDate(java.util.Calendar LastManagerRequestsSharedDate) {
      this.LastManagerRequestsSharedDate = LastManagerRequestsSharedDate;
      LastManagerRequestsSharedDate__is_set = true;
    }

    /**
     * element : LastModifiedBy of type {urn:sobject.enterprise.soap.sforce.com}User
     * java type: com.sforce.soap.enterprise.sobject.User
     */
    private static final com.sforce.ws.bind.TypeInfo LastModifiedBy__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","LastModifiedBy","urn:sobject.enterprise.soap.sforce.com","User",0,1,true);

    private boolean LastModifiedBy__is_set = false;

    private com.sforce.soap.enterprise.sobject.User LastModifiedBy;

    public com.sforce.soap.enterprise.sobject.User getLastModifiedBy() {
      return LastModifiedBy;
    }

    public void setLastModifiedBy(com.sforce.soap.enterprise.sobject.User LastModifiedBy) {
      this.LastModifiedBy = LastModifiedBy;
      LastModifiedBy__is_set = true;
    }

    /**
     * element : LastModifiedById of type {urn:enterprise.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo LastModifiedById__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","LastModifiedById","urn:enterprise.soap.sforce.com","ID",0,1,true);

    private boolean LastModifiedById__is_set = false;

    private java.lang.String LastModifiedById;

    public java.lang.String getLastModifiedById() {
      return LastModifiedById;
    }

    public void setLastModifiedById(java.lang.String LastModifiedById) {
      this.LastModifiedById = LastModifiedById;
      LastModifiedById__is_set = true;
    }

    /**
     * element : LastModifiedDate of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo LastModifiedDate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","LastModifiedDate","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true);

    private boolean LastModifiedDate__is_set = false;

    private java.util.Calendar LastModifiedDate;

    public java.util.Calendar getLastModifiedDate() {
      return LastModifiedDate;
    }

    public void setLastModifiedDate(java.util.Calendar LastModifiedDate) {
      this.LastModifiedDate = LastModifiedDate;
      LastModifiedDate__is_set = true;
    }

    /**
     * element : Name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo Name__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","Name","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean Name__is_set = false;

    private java.lang.String Name;

    public java.lang.String getName() {
      return Name;
    }

    public void setName(java.lang.String Name) {
      this.Name = Name;
      Name__is_set = true;
    }

    /**
     * element : Owner of type {urn:sobject.enterprise.soap.sforce.com}sObject
     * java type: com.sforce.soap.enterprise.sobject.SObject
     */
    private static final com.sforce.ws.bind.TypeInfo Owner__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","Owner","urn:sobject.enterprise.soap.sforce.com","sObject",0,1,true);

    private boolean Owner__is_set = false;

    private com.sforce.soap.enterprise.sobject.SObject Owner;

    public com.sforce.soap.enterprise.sobject.SObject getOwner() {
      return Owner;
    }

    public void setOwner(com.sforce.soap.enterprise.sobject.SObject Owner) {
      this.Owner = Owner;
      Owner__is_set = true;
    }

    /**
     * element : OwnerId of type {urn:enterprise.soap.sforce.com}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo OwnerId__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","OwnerId","urn:enterprise.soap.sforce.com","ID",0,1,true);

    private boolean OwnerId__is_set = false;

    private java.lang.String OwnerId;

    public java.lang.String getOwnerId() {
      return OwnerId;
    }

    public void setOwnerId(java.lang.String OwnerId) {
      this.OwnerId = OwnerId;
      OwnerId__is_set = true;
    }

    /**
     * element : PerformanceCycles of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo PerformanceCycles__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","PerformanceCycles","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean PerformanceCycles__is_set = false;

    private com.sforce.soap.enterprise.QueryResult PerformanceCycles;

    public com.sforce.soap.enterprise.QueryResult getPerformanceCycles() {
      return PerformanceCycles;
    }

    public void setPerformanceCycles(com.sforce.soap.enterprise.QueryResult PerformanceCycles) {
      this.PerformanceCycles = PerformanceCycles;
      PerformanceCycles__is_set = true;
    }

    /**
     * element : ProcessInstances of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo ProcessInstances__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","ProcessInstances","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean ProcessInstances__is_set = false;

    private com.sforce.soap.enterprise.QueryResult ProcessInstances;

    public com.sforce.soap.enterprise.QueryResult getProcessInstances() {
      return ProcessInstances;
    }

    public void setProcessInstances(com.sforce.soap.enterprise.QueryResult ProcessInstances) {
      this.ProcessInstances = ProcessInstances;
      ProcessInstances__is_set = true;
    }

    /**
     * element : ProcessSteps of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo ProcessSteps__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","ProcessSteps","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean ProcessSteps__is_set = false;

    private com.sforce.soap.enterprise.QueryResult ProcessSteps;

    public com.sforce.soap.enterprise.QueryResult getProcessSteps() {
      return ProcessSteps;
    }

    public void setProcessSteps(com.sforce.soap.enterprise.QueryResult ProcessSteps) {
      this.ProcessSteps = ProcessSteps;
      ProcessSteps__is_set = true;
    }

    /**
     * element : RelatedObjects of type {urn:enterprise.soap.sforce.com}QueryResult
     * java type: com.sforce.soap.enterprise.QueryResult
     */
    private static final com.sforce.ws.bind.TypeInfo RelatedObjects__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","RelatedObjects","urn:enterprise.soap.sforce.com","QueryResult",0,1,true);

    private boolean RelatedObjects__is_set = false;

    private com.sforce.soap.enterprise.QueryResult RelatedObjects;

    public com.sforce.soap.enterprise.QueryResult getRelatedObjects() {
      return RelatedObjects;
    }

    public void setRelatedObjects(com.sforce.soap.enterprise.QueryResult RelatedObjects) {
      this.RelatedObjects = RelatedObjects;
      RelatedObjects__is_set = true;
    }

    /**
     * element : State of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo State__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","State","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean State__is_set = false;

    private java.lang.String State;

    public java.lang.String getState() {
      return State;
    }

    public void setState(java.lang.String State) {
      this.State = State;
      State__is_set = true;
    }

    /**
     * element : SystemModstamp of type {http://www.w3.org/2001/XMLSchema}dateTime
     * java type: java.util.Calendar
     */
    private static final com.sforce.ws.bind.TypeInfo SystemModstamp__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","SystemModstamp","http://www.w3.org/2001/XMLSchema","dateTime",0,1,true);

    private boolean SystemModstamp__is_set = false;

    private java.util.Calendar SystemModstamp;

    public java.util.Calendar getSystemModstamp() {
      return SystemModstamp;
    }

    public void setSystemModstamp(java.util.Calendar SystemModstamp) {
      this.SystemModstamp = SystemModstamp;
      SystemModstamp__is_set = true;
    }

    /**
     * element : UserRecordAccess of type {urn:sobject.enterprise.soap.sforce.com}UserRecordAccess
     * java type: com.sforce.soap.enterprise.sobject.UserRecordAccess
     */
    private static final com.sforce.ws.bind.TypeInfo UserRecordAccess__typeInfo =
      new com.sforce.ws.bind.TypeInfo("urn:sobject.enterprise.soap.sforce.com","UserRecordAccess","urn:sobject.enterprise.soap.sforce.com","UserRecordAccess",0,1,true);

    private boolean UserRecordAccess__is_set = false;

    private com.sforce.soap.enterprise.sobject.UserRecordAccess UserRecordAccess;

    public com.sforce.soap.enterprise.sobject.UserRecordAccess getUserRecordAccess() {
      return UserRecordAccess;
    }

    public void setUserRecordAccess(com.sforce.soap.enterprise.sobject.UserRecordAccess UserRecordAccess) {
      this.UserRecordAccess = UserRecordAccess;
      UserRecordAccess__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "urn:sobject.enterprise.soap.sforce.com", "WorkPerformanceCycle");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, ActivityFrom__typeInfo, ActivityFrom, ActivityFrom__is_set);
       __typeMapper.writeObject(__out, ActivityTo__typeInfo, ActivityTo, ActivityTo__is_set);
       __typeMapper.writeObject(__out, AttachedContentDocuments__typeInfo, AttachedContentDocuments, AttachedContentDocuments__is_set);
       __typeMapper.writeObject(__out, CombinedAttachments__typeInfo, CombinedAttachments, CombinedAttachments__is_set);
       __typeMapper.writeObject(__out, CreatedBy__typeInfo, CreatedBy, CreatedBy__is_set);
       __typeMapper.writeString(__out, CreatedById__typeInfo, CreatedById, CreatedById__is_set);
       __typeMapper.writeObject(__out, CreatedDate__typeInfo, CreatedDate, CreatedDate__is_set);
       __typeMapper.writeString(__out, CurrentTask__typeInfo, CurrentTask, CurrentTask__is_set);
       __typeMapper.writeObject(__out, FeedSubscriptionsForEntity__typeInfo, FeedSubscriptionsForEntity, FeedSubscriptionsForEntity__is_set);
       __typeMapper.writeObject(__out, FeedbackQuestionSets__typeInfo, FeedbackQuestionSets, FeedbackQuestionSets__is_set);
       __typeMapper.writeObject(__out, Feeds__typeInfo, Feeds, Feeds__is_set);
       __typeMapper.writeObject(__out, Histories__typeInfo, Histories, Histories__is_set);
       __typeMapper.writeObject(__out, IsDeleted__typeInfo, IsDeleted, IsDeleted__is_set);
       __typeMapper.writeObject(__out, LastManagerRequestsSharedDate__typeInfo, LastManagerRequestsSharedDate, LastManagerRequestsSharedDate__is_set);
       __typeMapper.writeObject(__out, LastModifiedBy__typeInfo, LastModifiedBy, LastModifiedBy__is_set);
       __typeMapper.writeString(__out, LastModifiedById__typeInfo, LastModifiedById, LastModifiedById__is_set);
       __typeMapper.writeObject(__out, LastModifiedDate__typeInfo, LastModifiedDate, LastModifiedDate__is_set);
       __typeMapper.writeString(__out, Name__typeInfo, Name, Name__is_set);
       __typeMapper.writeObject(__out, Owner__typeInfo, Owner, Owner__is_set);
       __typeMapper.writeString(__out, OwnerId__typeInfo, OwnerId, OwnerId__is_set);
       __typeMapper.writeObject(__out, PerformanceCycles__typeInfo, PerformanceCycles, PerformanceCycles__is_set);
       __typeMapper.writeObject(__out, ProcessInstances__typeInfo, ProcessInstances, ProcessInstances__is_set);
       __typeMapper.writeObject(__out, ProcessSteps__typeInfo, ProcessSteps, ProcessSteps__is_set);
       __typeMapper.writeObject(__out, RelatedObjects__typeInfo, RelatedObjects, RelatedObjects__is_set);
       __typeMapper.writeString(__out, State__typeInfo, State, State__is_set);
       __typeMapper.writeObject(__out, SystemModstamp__typeInfo, SystemModstamp, SystemModstamp__is_set);
       __typeMapper.writeObject(__out, UserRecordAccess__typeInfo, UserRecordAccess, UserRecordAccess__is_set);
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
        super.loadFields(__in, __typeMapper);
        __in.peekTag();
        if (__typeMapper.isElement(__in, ActivityFrom__typeInfo)) {
            setActivityFrom((java.util.Calendar)__typeMapper.readObject(__in, ActivityFrom__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, ActivityTo__typeInfo)) {
            setActivityTo((java.util.Calendar)__typeMapper.readObject(__in, ActivityTo__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, AttachedContentDocuments__typeInfo)) {
            setAttachedContentDocuments((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, AttachedContentDocuments__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, CombinedAttachments__typeInfo)) {
            setCombinedAttachments((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, CombinedAttachments__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, CreatedBy__typeInfo)) {
            setCreatedBy((com.sforce.soap.enterprise.sobject.User)__typeMapper.readObject(__in, CreatedBy__typeInfo, com.sforce.soap.enterprise.sobject.User.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, CreatedById__typeInfo)) {
            setCreatedById(__typeMapper.readString(__in, CreatedById__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, CreatedDate__typeInfo)) {
            setCreatedDate((java.util.Calendar)__typeMapper.readObject(__in, CreatedDate__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, CurrentTask__typeInfo)) {
            setCurrentTask(__typeMapper.readString(__in, CurrentTask__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, FeedSubscriptionsForEntity__typeInfo)) {
            setFeedSubscriptionsForEntity((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, FeedSubscriptionsForEntity__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, FeedbackQuestionSets__typeInfo)) {
            setFeedbackQuestionSets((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, FeedbackQuestionSets__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, Feeds__typeInfo)) {
            setFeeds((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, Feeds__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, Histories__typeInfo)) {
            setHistories((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, Histories__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, IsDeleted__typeInfo)) {
            setIsDeleted((java.lang.Boolean)__typeMapper.readObject(__in, IsDeleted__typeInfo, java.lang.Boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, LastManagerRequestsSharedDate__typeInfo)) {
            setLastManagerRequestsSharedDate((java.util.Calendar)__typeMapper.readObject(__in, LastManagerRequestsSharedDate__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, LastModifiedBy__typeInfo)) {
            setLastModifiedBy((com.sforce.soap.enterprise.sobject.User)__typeMapper.readObject(__in, LastModifiedBy__typeInfo, com.sforce.soap.enterprise.sobject.User.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, LastModifiedById__typeInfo)) {
            setLastModifiedById(__typeMapper.readString(__in, LastModifiedById__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, LastModifiedDate__typeInfo)) {
            setLastModifiedDate((java.util.Calendar)__typeMapper.readObject(__in, LastModifiedDate__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, Name__typeInfo)) {
            setName(__typeMapper.readString(__in, Name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, Owner__typeInfo)) {
            setOwner((com.sforce.soap.enterprise.sobject.SObject)__typeMapper.readObject(__in, Owner__typeInfo, com.sforce.soap.enterprise.sobject.SObject.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, OwnerId__typeInfo)) {
            setOwnerId(__typeMapper.readString(__in, OwnerId__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, PerformanceCycles__typeInfo)) {
            setPerformanceCycles((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, PerformanceCycles__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, ProcessInstances__typeInfo)) {
            setProcessInstances((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, ProcessInstances__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, ProcessSteps__typeInfo)) {
            setProcessSteps((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, ProcessSteps__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, RelatedObjects__typeInfo)) {
            setRelatedObjects((com.sforce.soap.enterprise.QueryResult)__typeMapper.readObject(__in, RelatedObjects__typeInfo, com.sforce.soap.enterprise.QueryResult.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, State__typeInfo)) {
            setState(__typeMapper.readString(__in, State__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, SystemModstamp__typeInfo)) {
            setSystemModstamp((java.util.Calendar)__typeMapper.readObject(__in, SystemModstamp__typeInfo, java.util.Calendar.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, UserRecordAccess__typeInfo)) {
            setUserRecordAccess((com.sforce.soap.enterprise.sobject.UserRecordAccess)__typeMapper.readObject(__in, UserRecordAccess__typeInfo, com.sforce.soap.enterprise.sobject.UserRecordAccess.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[WorkPerformanceCycle ");
      sb.append(super.toString());sb.append(" ActivityFrom='").append(com.sforce.ws.util.Verbose.toString(ActivityFrom)).append("'\n");
      sb.append(" ActivityTo='").append(com.sforce.ws.util.Verbose.toString(ActivityTo)).append("'\n");
      sb.append(" AttachedContentDocuments='").append(com.sforce.ws.util.Verbose.toString(AttachedContentDocuments)).append("'\n");
      sb.append(" CombinedAttachments='").append(com.sforce.ws.util.Verbose.toString(CombinedAttachments)).append("'\n");
      sb.append(" CreatedBy='").append(com.sforce.ws.util.Verbose.toString(CreatedBy)).append("'\n");
      sb.append(" CreatedById='").append(com.sforce.ws.util.Verbose.toString(CreatedById)).append("'\n");
      sb.append(" CreatedDate='").append(com.sforce.ws.util.Verbose.toString(CreatedDate)).append("'\n");
      sb.append(" CurrentTask='").append(com.sforce.ws.util.Verbose.toString(CurrentTask)).append("'\n");
      sb.append(" FeedSubscriptionsForEntity='").append(com.sforce.ws.util.Verbose.toString(FeedSubscriptionsForEntity)).append("'\n");
      sb.append(" FeedbackQuestionSets='").append(com.sforce.ws.util.Verbose.toString(FeedbackQuestionSets)).append("'\n");
      sb.append(" Feeds='").append(com.sforce.ws.util.Verbose.toString(Feeds)).append("'\n");
      sb.append(" Histories='").append(com.sforce.ws.util.Verbose.toString(Histories)).append("'\n");
      sb.append(" IsDeleted='").append(com.sforce.ws.util.Verbose.toString(IsDeleted)).append("'\n");
      sb.append(" LastManagerRequestsSharedDate='").append(com.sforce.ws.util.Verbose.toString(LastManagerRequestsSharedDate)).append("'\n");
      sb.append(" LastModifiedBy='").append(com.sforce.ws.util.Verbose.toString(LastModifiedBy)).append("'\n");
      sb.append(" LastModifiedById='").append(com.sforce.ws.util.Verbose.toString(LastModifiedById)).append("'\n");
      sb.append(" LastModifiedDate='").append(com.sforce.ws.util.Verbose.toString(LastModifiedDate)).append("'\n");
      sb.append(" Name='").append(com.sforce.ws.util.Verbose.toString(Name)).append("'\n");
      sb.append(" Owner='").append(com.sforce.ws.util.Verbose.toString(Owner)).append("'\n");
      sb.append(" OwnerId='").append(com.sforce.ws.util.Verbose.toString(OwnerId)).append("'\n");
      sb.append(" PerformanceCycles='").append(com.sforce.ws.util.Verbose.toString(PerformanceCycles)).append("'\n");
      sb.append(" ProcessInstances='").append(com.sforce.ws.util.Verbose.toString(ProcessInstances)).append("'\n");
      sb.append(" ProcessSteps='").append(com.sforce.ws.util.Verbose.toString(ProcessSteps)).append("'\n");
      sb.append(" RelatedObjects='").append(com.sforce.ws.util.Verbose.toString(RelatedObjects)).append("'\n");
      sb.append(" State='").append(com.sforce.ws.util.Verbose.toString(State)).append("'\n");
      sb.append(" SystemModstamp='").append(com.sforce.ws.util.Verbose.toString(SystemModstamp)).append("'\n");
      sb.append(" UserRecordAccess='").append(com.sforce.ws.util.Verbose.toString(UserRecordAccess)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}