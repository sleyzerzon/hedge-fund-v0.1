package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class NameSettings extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public NameSettings() {}

    /**
     * element : enableMiddleName of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableMiddleName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableMiddleName","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableMiddleName__is_set = false;

    private boolean enableMiddleName;

    public boolean getEnableMiddleName() {
      return enableMiddleName;
    }

    public boolean isEnableMiddleName() {
      return enableMiddleName;
    }

    public void setEnableMiddleName(boolean enableMiddleName) {
      this.enableMiddleName = enableMiddleName;
      enableMiddleName__is_set = true;
    }

    /**
     * element : enableNameSuffix of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableNameSuffix__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableNameSuffix","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableNameSuffix__is_set = false;

    private boolean enableNameSuffix;

    public boolean getEnableNameSuffix() {
      return enableNameSuffix;
    }

    public boolean isEnableNameSuffix() {
      return enableNameSuffix;
    }

    public void setEnableNameSuffix(boolean enableNameSuffix) {
      this.enableNameSuffix = enableNameSuffix;
      enableNameSuffix__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "NameSettings");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeBoolean(__out, enableMiddleName__typeInfo, enableMiddleName, enableMiddleName__is_set);
       __typeMapper.writeBoolean(__out, enableNameSuffix__typeInfo, enableNameSuffix, enableNameSuffix__is_set);
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
        if (__typeMapper.isElement(__in, enableMiddleName__typeInfo)) {
            setEnableMiddleName(__typeMapper.readBoolean(__in, enableMiddleName__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, enableNameSuffix__typeInfo)) {
            setEnableNameSuffix(__typeMapper.readBoolean(__in, enableNameSuffix__typeInfo, boolean.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[NameSettings ");
      sb.append(super.toString());sb.append(" enableMiddleName='").append(com.sforce.ws.util.Verbose.toString(enableMiddleName)).append("'\n");
      sb.append(" enableNameSuffix='").append(com.sforce.ws.util.Verbose.toString(enableNameSuffix)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
