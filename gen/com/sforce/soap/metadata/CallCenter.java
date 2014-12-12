package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class CallCenter extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public CallCenter() {}

    /**
     * element : adapterUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo adapterUrl__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","adapterUrl","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean adapterUrl__is_set = false;

    private java.lang.String adapterUrl;

    public java.lang.String getAdapterUrl() {
      return adapterUrl;
    }

    public void setAdapterUrl(java.lang.String adapterUrl) {
      this.adapterUrl = adapterUrl;
      adapterUrl__is_set = true;
    }

    /**
     * element : customSettings of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo customSettings__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","customSettings","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean customSettings__is_set = false;

    private java.lang.String customSettings;

    public java.lang.String getCustomSettings() {
      return customSettings;
    }

    public void setCustomSettings(java.lang.String customSettings) {
      this.customSettings = customSettings;
      customSettings__is_set = true;
    }

    /**
     * element : displayName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo displayName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","displayName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean displayName__is_set = false;

    private java.lang.String displayName;

    public java.lang.String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(java.lang.String displayName) {
      this.displayName = displayName;
      displayName__is_set = true;
    }

    /**
     * element : displayNameLabel of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo displayNameLabel__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","displayNameLabel","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean displayNameLabel__is_set = false;

    private java.lang.String displayNameLabel;

    public java.lang.String getDisplayNameLabel() {
      return displayNameLabel;
    }

    public void setDisplayNameLabel(java.lang.String displayNameLabel) {
      this.displayNameLabel = displayNameLabel;
      displayNameLabel__is_set = true;
    }

    /**
     * element : internalNameLabel of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo internalNameLabel__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","internalNameLabel","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean internalNameLabel__is_set = false;

    private java.lang.String internalNameLabel;

    public java.lang.String getInternalNameLabel() {
      return internalNameLabel;
    }

    public void setInternalNameLabel(java.lang.String internalNameLabel) {
      this.internalNameLabel = internalNameLabel;
      internalNameLabel__is_set = true;
    }

    /**
     * element : sections of type {http://soap.sforce.com/2006/04/metadata}CallCenterSection
     * java type: com.sforce.soap.metadata.CallCenterSection[]
     */
    private static final com.sforce.ws.bind.TypeInfo sections__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","sections","http://soap.sforce.com/2006/04/metadata","CallCenterSection",0,-1,true);

    private boolean sections__is_set = false;

    private com.sforce.soap.metadata.CallCenterSection[] sections = new com.sforce.soap.metadata.CallCenterSection[0];

    public com.sforce.soap.metadata.CallCenterSection[] getSections() {
      return sections;
    }

    public void setSections(com.sforce.soap.metadata.CallCenterSection[] sections) {
      this.sections = sections;
      sections__is_set = true;
    }

    /**
     * element : version of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo version__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","version","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean version__is_set = false;

    private java.lang.String version;

    public java.lang.String getVersion() {
      return version;
    }

    public void setVersion(java.lang.String version) {
      this.version = version;
      version__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "CallCenter");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeString(__out, adapterUrl__typeInfo, adapterUrl, adapterUrl__is_set);
       __typeMapper.writeString(__out, customSettings__typeInfo, customSettings, customSettings__is_set);
       __typeMapper.writeString(__out, displayName__typeInfo, displayName, displayName__is_set);
       __typeMapper.writeString(__out, displayNameLabel__typeInfo, displayNameLabel, displayNameLabel__is_set);
       __typeMapper.writeString(__out, internalNameLabel__typeInfo, internalNameLabel, internalNameLabel__is_set);
       __typeMapper.writeObject(__out, sections__typeInfo, sections, sections__is_set);
       __typeMapper.writeString(__out, version__typeInfo, version, version__is_set);
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
        if (__typeMapper.isElement(__in, adapterUrl__typeInfo)) {
            setAdapterUrl(__typeMapper.readString(__in, adapterUrl__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, customSettings__typeInfo)) {
            setCustomSettings(__typeMapper.readString(__in, customSettings__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, displayName__typeInfo)) {
            setDisplayName(__typeMapper.readString(__in, displayName__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, displayNameLabel__typeInfo)) {
            setDisplayNameLabel(__typeMapper.readString(__in, displayNameLabel__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, internalNameLabel__typeInfo)) {
            setInternalNameLabel(__typeMapper.readString(__in, internalNameLabel__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, sections__typeInfo)) {
            setSections((com.sforce.soap.metadata.CallCenterSection[])__typeMapper.readObject(__in, sections__typeInfo, com.sforce.soap.metadata.CallCenterSection[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, version__typeInfo)) {
            setVersion(__typeMapper.readString(__in, version__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[CallCenter ");
      sb.append(super.toString());sb.append(" adapterUrl='").append(com.sforce.ws.util.Verbose.toString(adapterUrl)).append("'\n");
      sb.append(" customSettings='").append(com.sforce.ws.util.Verbose.toString(customSettings)).append("'\n");
      sb.append(" displayName='").append(com.sforce.ws.util.Verbose.toString(displayName)).append("'\n");
      sb.append(" displayNameLabel='").append(com.sforce.ws.util.Verbose.toString(displayNameLabel)).append("'\n");
      sb.append(" internalNameLabel='").append(com.sforce.ws.util.Verbose.toString(internalNameLabel)).append("'\n");
      sb.append(" sections='").append(com.sforce.ws.util.Verbose.toString(sections)).append("'\n");
      sb.append(" version='").append(com.sforce.ws.util.Verbose.toString(version)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
