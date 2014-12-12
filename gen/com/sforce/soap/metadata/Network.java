package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class Network extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public Network() {}

    /**
     * element : allowMembersToFlag of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo allowMembersToFlag__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","allowMembersToFlag","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean allowMembersToFlag__is_set = false;

    private boolean allowMembersToFlag;

    public boolean getAllowMembersToFlag() {
      return allowMembersToFlag;
    }

    public boolean isAllowMembersToFlag() {
      return allowMembersToFlag;
    }

    public void setAllowMembersToFlag(boolean allowMembersToFlag) {
      this.allowMembersToFlag = allowMembersToFlag;
      allowMembersToFlag__is_set = true;
    }

    /**
     * element : branding of type {http://soap.sforce.com/2006/04/metadata}Branding
     * java type: com.sforce.soap.metadata.Branding
     */
    private static final com.sforce.ws.bind.TypeInfo branding__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","branding","http://soap.sforce.com/2006/04/metadata","Branding",0,1,true);

    private boolean branding__is_set = false;

    private com.sforce.soap.metadata.Branding branding;

    public com.sforce.soap.metadata.Branding getBranding() {
      return branding;
    }

    public void setBranding(com.sforce.soap.metadata.Branding branding) {
      this.branding = branding;
      branding__is_set = true;
    }

    /**
     * element : caseCommentEmailTemplate of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo caseCommentEmailTemplate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","caseCommentEmailTemplate","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean caseCommentEmailTemplate__is_set = false;

    private java.lang.String caseCommentEmailTemplate;

    public java.lang.String getCaseCommentEmailTemplate() {
      return caseCommentEmailTemplate;
    }

    public void setCaseCommentEmailTemplate(java.lang.String caseCommentEmailTemplate) {
      this.caseCommentEmailTemplate = caseCommentEmailTemplate;
      caseCommentEmailTemplate__is_set = true;
    }

    /**
     * element : changePasswordTemplate of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo changePasswordTemplate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","changePasswordTemplate","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean changePasswordTemplate__is_set = false;

    private java.lang.String changePasswordTemplate;

    public java.lang.String getChangePasswordTemplate() {
      return changePasswordTemplate;
    }

    public void setChangePasswordTemplate(java.lang.String changePasswordTemplate) {
      this.changePasswordTemplate = changePasswordTemplate;
      changePasswordTemplate__is_set = true;
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
     * element : emailSenderAddress of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo emailSenderAddress__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","emailSenderAddress","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean emailSenderAddress__is_set = false;

    private java.lang.String emailSenderAddress;

    public java.lang.String getEmailSenderAddress() {
      return emailSenderAddress;
    }

    public void setEmailSenderAddress(java.lang.String emailSenderAddress) {
      this.emailSenderAddress = emailSenderAddress;
      emailSenderAddress__is_set = true;
    }

    /**
     * element : emailSenderName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo emailSenderName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","emailSenderName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean emailSenderName__is_set = false;

    private java.lang.String emailSenderName;

    public java.lang.String getEmailSenderName() {
      return emailSenderName;
    }

    public void setEmailSenderName(java.lang.String emailSenderName) {
      this.emailSenderName = emailSenderName;
      emailSenderName__is_set = true;
    }

    /**
     * element : enableGuestChatter of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableGuestChatter__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableGuestChatter","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableGuestChatter__is_set = false;

    private boolean enableGuestChatter;

    public boolean getEnableGuestChatter() {
      return enableGuestChatter;
    }

    public boolean isEnableGuestChatter() {
      return enableGuestChatter;
    }

    public void setEnableGuestChatter(boolean enableGuestChatter) {
      this.enableGuestChatter = enableGuestChatter;
      enableGuestChatter__is_set = true;
    }

    /**
     * element : enableInvitation of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableInvitation__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableInvitation","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableInvitation__is_set = false;

    private boolean enableInvitation;

    public boolean getEnableInvitation() {
      return enableInvitation;
    }

    public boolean isEnableInvitation() {
      return enableInvitation;
    }

    public void setEnableInvitation(boolean enableInvitation) {
      this.enableInvitation = enableInvitation;
      enableInvitation__is_set = true;
    }

    /**
     * element : enableKnowledgeable of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableKnowledgeable__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableKnowledgeable","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableKnowledgeable__is_set = false;

    private boolean enableKnowledgeable;

    public boolean getEnableKnowledgeable() {
      return enableKnowledgeable;
    }

    public boolean isEnableKnowledgeable() {
      return enableKnowledgeable;
    }

    public void setEnableKnowledgeable(boolean enableKnowledgeable) {
      this.enableKnowledgeable = enableKnowledgeable;
      enableKnowledgeable__is_set = true;
    }

    /**
     * element : enableNicknameDisplay of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableNicknameDisplay__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableNicknameDisplay","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableNicknameDisplay__is_set = false;

    private boolean enableNicknameDisplay;

    public boolean getEnableNicknameDisplay() {
      return enableNicknameDisplay;
    }

    public boolean isEnableNicknameDisplay() {
      return enableNicknameDisplay;
    }

    public void setEnableNicknameDisplay(boolean enableNicknameDisplay) {
      this.enableNicknameDisplay = enableNicknameDisplay;
      enableNicknameDisplay__is_set = true;
    }

    /**
     * element : enablePrivateMessages of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enablePrivateMessages__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enablePrivateMessages","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enablePrivateMessages__is_set = false;

    private boolean enablePrivateMessages;

    public boolean getEnablePrivateMessages() {
      return enablePrivateMessages;
    }

    public boolean isEnablePrivateMessages() {
      return enablePrivateMessages;
    }

    public void setEnablePrivateMessages(boolean enablePrivateMessages) {
      this.enablePrivateMessages = enablePrivateMessages;
      enablePrivateMessages__is_set = true;
    }

    /**
     * element : enableReputation of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableReputation__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableReputation","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableReputation__is_set = false;

    private boolean enableReputation;

    public boolean getEnableReputation() {
      return enableReputation;
    }

    public boolean isEnableReputation() {
      return enableReputation;
    }

    public void setEnableReputation(boolean enableReputation) {
      this.enableReputation = enableReputation;
      enableReputation__is_set = true;
    }

    /**
     * element : feedChannel of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo feedChannel__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","feedChannel","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean feedChannel__is_set = false;

    private java.lang.String feedChannel;

    public java.lang.String getFeedChannel() {
      return feedChannel;
    }

    public void setFeedChannel(java.lang.String feedChannel) {
      this.feedChannel = feedChannel;
      feedChannel__is_set = true;
    }

    /**
     * element : forgotPasswordTemplate of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo forgotPasswordTemplate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","forgotPasswordTemplate","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean forgotPasswordTemplate__is_set = false;

    private java.lang.String forgotPasswordTemplate;

    public java.lang.String getForgotPasswordTemplate() {
      return forgotPasswordTemplate;
    }

    public void setForgotPasswordTemplate(java.lang.String forgotPasswordTemplate) {
      this.forgotPasswordTemplate = forgotPasswordTemplate;
      forgotPasswordTemplate__is_set = true;
    }

    /**
     * element : logoutUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo logoutUrl__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","logoutUrl","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean logoutUrl__is_set = false;

    private java.lang.String logoutUrl;

    public java.lang.String getLogoutUrl() {
      return logoutUrl;
    }

    public void setLogoutUrl(java.lang.String logoutUrl) {
      this.logoutUrl = logoutUrl;
      logoutUrl__is_set = true;
    }

    /**
     * element : networkMemberGroups of type {http://soap.sforce.com/2006/04/metadata}NetworkMemberGroup
     * java type: com.sforce.soap.metadata.NetworkMemberGroup
     */
    private static final com.sforce.ws.bind.TypeInfo networkMemberGroups__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","networkMemberGroups","http://soap.sforce.com/2006/04/metadata","NetworkMemberGroup",0,1,true);

    private boolean networkMemberGroups__is_set = false;

    private com.sforce.soap.metadata.NetworkMemberGroup networkMemberGroups;

    public com.sforce.soap.metadata.NetworkMemberGroup getNetworkMemberGroups() {
      return networkMemberGroups;
    }

    public void setNetworkMemberGroups(com.sforce.soap.metadata.NetworkMemberGroup networkMemberGroups) {
      this.networkMemberGroups = networkMemberGroups;
      networkMemberGroups__is_set = true;
    }

    /**
     * element : newSenderAddress of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo newSenderAddress__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","newSenderAddress","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean newSenderAddress__is_set = false;

    private java.lang.String newSenderAddress;

    public java.lang.String getNewSenderAddress() {
      return newSenderAddress;
    }

    public void setNewSenderAddress(java.lang.String newSenderAddress) {
      this.newSenderAddress = newSenderAddress;
      newSenderAddress__is_set = true;
    }

    /**
     * element : picassoSite of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo picassoSite__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","picassoSite","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean picassoSite__is_set = false;

    private java.lang.String picassoSite;

    public java.lang.String getPicassoSite() {
      return picassoSite;
    }

    public void setPicassoSite(java.lang.String picassoSite) {
      this.picassoSite = picassoSite;
      picassoSite__is_set = true;
    }

    /**
     * element : reputationLevels of type {http://soap.sforce.com/2006/04/metadata}ReputationLevelDefinitions
     * java type: com.sforce.soap.metadata.ReputationLevelDefinitions
     */
    private static final com.sforce.ws.bind.TypeInfo reputationLevels__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","reputationLevels","http://soap.sforce.com/2006/04/metadata","ReputationLevelDefinitions",0,1,true);

    private boolean reputationLevels__is_set = false;

    private com.sforce.soap.metadata.ReputationLevelDefinitions reputationLevels;

    public com.sforce.soap.metadata.ReputationLevelDefinitions getReputationLevels() {
      return reputationLevels;
    }

    public void setReputationLevels(com.sforce.soap.metadata.ReputationLevelDefinitions reputationLevels) {
      this.reputationLevels = reputationLevels;
      reputationLevels__is_set = true;
    }

    /**
     * element : reputationPointsRules of type {http://soap.sforce.com/2006/04/metadata}ReputationPointsRules
     * java type: com.sforce.soap.metadata.ReputationPointsRules
     */
    private static final com.sforce.ws.bind.TypeInfo reputationPointsRules__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","reputationPointsRules","http://soap.sforce.com/2006/04/metadata","ReputationPointsRules",0,1,true);

    private boolean reputationPointsRules__is_set = false;

    private com.sforce.soap.metadata.ReputationPointsRules reputationPointsRules;

    public com.sforce.soap.metadata.ReputationPointsRules getReputationPointsRules() {
      return reputationPointsRules;
    }

    public void setReputationPointsRules(com.sforce.soap.metadata.ReputationPointsRules reputationPointsRules) {
      this.reputationPointsRules = reputationPointsRules;
      reputationPointsRules__is_set = true;
    }

    /**
     * element : selfRegProfile of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo selfRegProfile__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","selfRegProfile","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean selfRegProfile__is_set = false;

    private java.lang.String selfRegProfile;

    public java.lang.String getSelfRegProfile() {
      return selfRegProfile;
    }

    public void setSelfRegProfile(java.lang.String selfRegProfile) {
      this.selfRegProfile = selfRegProfile;
      selfRegProfile__is_set = true;
    }

    /**
     * element : selfRegistration of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo selfRegistration__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","selfRegistration","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean selfRegistration__is_set = false;

    private boolean selfRegistration;

    public boolean getSelfRegistration() {
      return selfRegistration;
    }

    public boolean isSelfRegistration() {
      return selfRegistration;
    }

    public void setSelfRegistration(boolean selfRegistration) {
      this.selfRegistration = selfRegistration;
      selfRegistration__is_set = true;
    }

    /**
     * element : sendWelcomeEmail of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo sendWelcomeEmail__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","sendWelcomeEmail","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean sendWelcomeEmail__is_set = false;

    private boolean sendWelcomeEmail;

    public boolean getSendWelcomeEmail() {
      return sendWelcomeEmail;
    }

    public boolean isSendWelcomeEmail() {
      return sendWelcomeEmail;
    }

    public void setSendWelcomeEmail(boolean sendWelcomeEmail) {
      this.sendWelcomeEmail = sendWelcomeEmail;
      sendWelcomeEmail__is_set = true;
    }

    /**
     * element : site of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo site__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","site","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean site__is_set = false;

    private java.lang.String site;

    public java.lang.String getSite() {
      return site;
    }

    public void setSite(java.lang.String site) {
      this.site = site;
      site__is_set = true;
    }

    /**
     * element : status of type {http://soap.sforce.com/2006/04/metadata}NetworkStatus
     * java type: com.sforce.soap.metadata.NetworkStatus
     */
    private static final com.sforce.ws.bind.TypeInfo status__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","status","http://soap.sforce.com/2006/04/metadata","NetworkStatus",1,1,true);

    private boolean status__is_set = false;

    private com.sforce.soap.metadata.NetworkStatus status;

    public com.sforce.soap.metadata.NetworkStatus getStatus() {
      return status;
    }

    public void setStatus(com.sforce.soap.metadata.NetworkStatus status) {
      this.status = status;
      status__is_set = true;
    }

    /**
     * element : tabs of type {http://soap.sforce.com/2006/04/metadata}NetworkTabSet
     * java type: com.sforce.soap.metadata.NetworkTabSet
     */
    private static final com.sforce.ws.bind.TypeInfo tabs__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","tabs","http://soap.sforce.com/2006/04/metadata","NetworkTabSet",1,1,true);

    private boolean tabs__is_set = false;

    private com.sforce.soap.metadata.NetworkTabSet tabs;

    public com.sforce.soap.metadata.NetworkTabSet getTabs() {
      return tabs;
    }

    public void setTabs(com.sforce.soap.metadata.NetworkTabSet tabs) {
      this.tabs = tabs;
      tabs__is_set = true;
    }

    /**
     * element : urlPathPrefix of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo urlPathPrefix__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","urlPathPrefix","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean urlPathPrefix__is_set = false;

    private java.lang.String urlPathPrefix;

    public java.lang.String getUrlPathPrefix() {
      return urlPathPrefix;
    }

    public void setUrlPathPrefix(java.lang.String urlPathPrefix) {
      this.urlPathPrefix = urlPathPrefix;
      urlPathPrefix__is_set = true;
    }

    /**
     * element : welcomeTemplate of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo welcomeTemplate__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","welcomeTemplate","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean welcomeTemplate__is_set = false;

    private java.lang.String welcomeTemplate;

    public java.lang.String getWelcomeTemplate() {
      return welcomeTemplate;
    }

    public void setWelcomeTemplate(java.lang.String welcomeTemplate) {
      this.welcomeTemplate = welcomeTemplate;
      welcomeTemplate__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "Network");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeBoolean(__out, allowMembersToFlag__typeInfo, allowMembersToFlag, allowMembersToFlag__is_set);
       __typeMapper.writeObject(__out, branding__typeInfo, branding, branding__is_set);
       __typeMapper.writeString(__out, caseCommentEmailTemplate__typeInfo, caseCommentEmailTemplate, caseCommentEmailTemplate__is_set);
       __typeMapper.writeString(__out, changePasswordTemplate__typeInfo, changePasswordTemplate, changePasswordTemplate__is_set);
       __typeMapper.writeString(__out, description__typeInfo, description, description__is_set);
       __typeMapper.writeString(__out, emailSenderAddress__typeInfo, emailSenderAddress, emailSenderAddress__is_set);
       __typeMapper.writeString(__out, emailSenderName__typeInfo, emailSenderName, emailSenderName__is_set);
       __typeMapper.writeBoolean(__out, enableGuestChatter__typeInfo, enableGuestChatter, enableGuestChatter__is_set);
       __typeMapper.writeBoolean(__out, enableInvitation__typeInfo, enableInvitation, enableInvitation__is_set);
       __typeMapper.writeBoolean(__out, enableKnowledgeable__typeInfo, enableKnowledgeable, enableKnowledgeable__is_set);
       __typeMapper.writeBoolean(__out, enableNicknameDisplay__typeInfo, enableNicknameDisplay, enableNicknameDisplay__is_set);
       __typeMapper.writeBoolean(__out, enablePrivateMessages__typeInfo, enablePrivateMessages, enablePrivateMessages__is_set);
       __typeMapper.writeBoolean(__out, enableReputation__typeInfo, enableReputation, enableReputation__is_set);
       __typeMapper.writeString(__out, feedChannel__typeInfo, feedChannel, feedChannel__is_set);
       __typeMapper.writeString(__out, forgotPasswordTemplate__typeInfo, forgotPasswordTemplate, forgotPasswordTemplate__is_set);
       __typeMapper.writeString(__out, logoutUrl__typeInfo, logoutUrl, logoutUrl__is_set);
       __typeMapper.writeObject(__out, networkMemberGroups__typeInfo, networkMemberGroups, networkMemberGroups__is_set);
       __typeMapper.writeString(__out, newSenderAddress__typeInfo, newSenderAddress, newSenderAddress__is_set);
       __typeMapper.writeString(__out, picassoSite__typeInfo, picassoSite, picassoSite__is_set);
       __typeMapper.writeObject(__out, reputationLevels__typeInfo, reputationLevels, reputationLevels__is_set);
       __typeMapper.writeObject(__out, reputationPointsRules__typeInfo, reputationPointsRules, reputationPointsRules__is_set);
       __typeMapper.writeString(__out, selfRegProfile__typeInfo, selfRegProfile, selfRegProfile__is_set);
       __typeMapper.writeBoolean(__out, selfRegistration__typeInfo, selfRegistration, selfRegistration__is_set);
       __typeMapper.writeBoolean(__out, sendWelcomeEmail__typeInfo, sendWelcomeEmail, sendWelcomeEmail__is_set);
       __typeMapper.writeString(__out, site__typeInfo, site, site__is_set);
       __typeMapper.writeObject(__out, status__typeInfo, status, status__is_set);
       __typeMapper.writeObject(__out, tabs__typeInfo, tabs, tabs__is_set);
       __typeMapper.writeString(__out, urlPathPrefix__typeInfo, urlPathPrefix, urlPathPrefix__is_set);
       __typeMapper.writeString(__out, welcomeTemplate__typeInfo, welcomeTemplate, welcomeTemplate__is_set);
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
        if (__typeMapper.isElement(__in, allowMembersToFlag__typeInfo)) {
            setAllowMembersToFlag(__typeMapper.readBoolean(__in, allowMembersToFlag__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, branding__typeInfo)) {
            setBranding((com.sforce.soap.metadata.Branding)__typeMapper.readObject(__in, branding__typeInfo, com.sforce.soap.metadata.Branding.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, caseCommentEmailTemplate__typeInfo)) {
            setCaseCommentEmailTemplate(__typeMapper.readString(__in, caseCommentEmailTemplate__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, changePasswordTemplate__typeInfo)) {
            setChangePasswordTemplate(__typeMapper.readString(__in, changePasswordTemplate__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, description__typeInfo)) {
            setDescription(__typeMapper.readString(__in, description__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, emailSenderAddress__typeInfo)) {
            setEmailSenderAddress(__typeMapper.readString(__in, emailSenderAddress__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, emailSenderName__typeInfo)) {
            setEmailSenderName(__typeMapper.readString(__in, emailSenderName__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableGuestChatter__typeInfo)) {
            setEnableGuestChatter(__typeMapper.readBoolean(__in, enableGuestChatter__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableInvitation__typeInfo)) {
            setEnableInvitation(__typeMapper.readBoolean(__in, enableInvitation__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableKnowledgeable__typeInfo)) {
            setEnableKnowledgeable(__typeMapper.readBoolean(__in, enableKnowledgeable__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableNicknameDisplay__typeInfo)) {
            setEnableNicknameDisplay(__typeMapper.readBoolean(__in, enableNicknameDisplay__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enablePrivateMessages__typeInfo)) {
            setEnablePrivateMessages(__typeMapper.readBoolean(__in, enablePrivateMessages__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableReputation__typeInfo)) {
            setEnableReputation(__typeMapper.readBoolean(__in, enableReputation__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, feedChannel__typeInfo)) {
            setFeedChannel(__typeMapper.readString(__in, feedChannel__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, forgotPasswordTemplate__typeInfo)) {
            setForgotPasswordTemplate(__typeMapper.readString(__in, forgotPasswordTemplate__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, logoutUrl__typeInfo)) {
            setLogoutUrl(__typeMapper.readString(__in, logoutUrl__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, networkMemberGroups__typeInfo)) {
            setNetworkMemberGroups((com.sforce.soap.metadata.NetworkMemberGroup)__typeMapper.readObject(__in, networkMemberGroups__typeInfo, com.sforce.soap.metadata.NetworkMemberGroup.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, newSenderAddress__typeInfo)) {
            setNewSenderAddress(__typeMapper.readString(__in, newSenderAddress__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, picassoSite__typeInfo)) {
            setPicassoSite(__typeMapper.readString(__in, picassoSite__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, reputationLevels__typeInfo)) {
            setReputationLevels((com.sforce.soap.metadata.ReputationLevelDefinitions)__typeMapper.readObject(__in, reputationLevels__typeInfo, com.sforce.soap.metadata.ReputationLevelDefinitions.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, reputationPointsRules__typeInfo)) {
            setReputationPointsRules((com.sforce.soap.metadata.ReputationPointsRules)__typeMapper.readObject(__in, reputationPointsRules__typeInfo, com.sforce.soap.metadata.ReputationPointsRules.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, selfRegProfile__typeInfo)) {
            setSelfRegProfile(__typeMapper.readString(__in, selfRegProfile__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, selfRegistration__typeInfo)) {
            setSelfRegistration(__typeMapper.readBoolean(__in, selfRegistration__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, sendWelcomeEmail__typeInfo)) {
            setSendWelcomeEmail(__typeMapper.readBoolean(__in, sendWelcomeEmail__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, site__typeInfo)) {
            setSite(__typeMapper.readString(__in, site__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, status__typeInfo)) {
            setStatus((com.sforce.soap.metadata.NetworkStatus)__typeMapper.readObject(__in, status__typeInfo, com.sforce.soap.metadata.NetworkStatus.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, tabs__typeInfo)) {
            setTabs((com.sforce.soap.metadata.NetworkTabSet)__typeMapper.readObject(__in, tabs__typeInfo, com.sforce.soap.metadata.NetworkTabSet.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, urlPathPrefix__typeInfo)) {
            setUrlPathPrefix(__typeMapper.readString(__in, urlPathPrefix__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, welcomeTemplate__typeInfo)) {
            setWelcomeTemplate(__typeMapper.readString(__in, welcomeTemplate__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[Network ");
      sb.append(super.toString());sb.append(" allowMembersToFlag='").append(com.sforce.ws.util.Verbose.toString(allowMembersToFlag)).append("'\n");
      sb.append(" branding='").append(com.sforce.ws.util.Verbose.toString(branding)).append("'\n");
      sb.append(" caseCommentEmailTemplate='").append(com.sforce.ws.util.Verbose.toString(caseCommentEmailTemplate)).append("'\n");
      sb.append(" changePasswordTemplate='").append(com.sforce.ws.util.Verbose.toString(changePasswordTemplate)).append("'\n");
      sb.append(" description='").append(com.sforce.ws.util.Verbose.toString(description)).append("'\n");
      sb.append(" emailSenderAddress='").append(com.sforce.ws.util.Verbose.toString(emailSenderAddress)).append("'\n");
      sb.append(" emailSenderName='").append(com.sforce.ws.util.Verbose.toString(emailSenderName)).append("'\n");
      sb.append(" enableGuestChatter='").append(com.sforce.ws.util.Verbose.toString(enableGuestChatter)).append("'\n");
      sb.append(" enableInvitation='").append(com.sforce.ws.util.Verbose.toString(enableInvitation)).append("'\n");
      sb.append(" enableKnowledgeable='").append(com.sforce.ws.util.Verbose.toString(enableKnowledgeable)).append("'\n");
      sb.append(" enableNicknameDisplay='").append(com.sforce.ws.util.Verbose.toString(enableNicknameDisplay)).append("'\n");
      sb.append(" enablePrivateMessages='").append(com.sforce.ws.util.Verbose.toString(enablePrivateMessages)).append("'\n");
      sb.append(" enableReputation='").append(com.sforce.ws.util.Verbose.toString(enableReputation)).append("'\n");
      sb.append(" feedChannel='").append(com.sforce.ws.util.Verbose.toString(feedChannel)).append("'\n");
      sb.append(" forgotPasswordTemplate='").append(com.sforce.ws.util.Verbose.toString(forgotPasswordTemplate)).append("'\n");
      sb.append(" logoutUrl='").append(com.sforce.ws.util.Verbose.toString(logoutUrl)).append("'\n");
      sb.append(" networkMemberGroups='").append(com.sforce.ws.util.Verbose.toString(networkMemberGroups)).append("'\n");
      sb.append(" newSenderAddress='").append(com.sforce.ws.util.Verbose.toString(newSenderAddress)).append("'\n");
      sb.append(" picassoSite='").append(com.sforce.ws.util.Verbose.toString(picassoSite)).append("'\n");
      sb.append(" reputationLevels='").append(com.sforce.ws.util.Verbose.toString(reputationLevels)).append("'\n");
      sb.append(" reputationPointsRules='").append(com.sforce.ws.util.Verbose.toString(reputationPointsRules)).append("'\n");
      sb.append(" selfRegProfile='").append(com.sforce.ws.util.Verbose.toString(selfRegProfile)).append("'\n");
      sb.append(" selfRegistration='").append(com.sforce.ws.util.Verbose.toString(selfRegistration)).append("'\n");
      sb.append(" sendWelcomeEmail='").append(com.sforce.ws.util.Verbose.toString(sendWelcomeEmail)).append("'\n");
      sb.append(" site='").append(com.sforce.ws.util.Verbose.toString(site)).append("'\n");
      sb.append(" status='").append(com.sforce.ws.util.Verbose.toString(status)).append("'\n");
      sb.append(" tabs='").append(com.sforce.ws.util.Verbose.toString(tabs)).append("'\n");
      sb.append(" urlPathPrefix='").append(com.sforce.ws.util.Verbose.toString(urlPathPrefix)).append("'\n");
      sb.append(" welcomeTemplate='").append(com.sforce.ws.util.Verbose.toString(welcomeTemplate)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
