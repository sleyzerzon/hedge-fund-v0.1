package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FlowRecordDelete extends com.sforce.soap.metadata.FlowNode {

    /**
     * Constructor
     */
    public FlowRecordDelete() {}

    /**
     * element : connector of type {http://soap.sforce.com/2006/04/metadata}FlowConnector
     * java type: com.sforce.soap.metadata.FlowConnector
     */
    private static final com.sforce.ws.bind.TypeInfo connector__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","connector","http://soap.sforce.com/2006/04/metadata","FlowConnector",0,1,true);

    private boolean connector__is_set = false;

    private com.sforce.soap.metadata.FlowConnector connector;

    public com.sforce.soap.metadata.FlowConnector getConnector() {
      return connector;
    }

    public void setConnector(com.sforce.soap.metadata.FlowConnector connector) {
      this.connector = connector;
      connector__is_set = true;
    }

    /**
     * element : faultConnector of type {http://soap.sforce.com/2006/04/metadata}FlowConnector
     * java type: com.sforce.soap.metadata.FlowConnector
     */
    private static final com.sforce.ws.bind.TypeInfo faultConnector__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","faultConnector","http://soap.sforce.com/2006/04/metadata","FlowConnector",0,1,true);

    private boolean faultConnector__is_set = false;

    private com.sforce.soap.metadata.FlowConnector faultConnector;

    public com.sforce.soap.metadata.FlowConnector getFaultConnector() {
      return faultConnector;
    }

    public void setFaultConnector(com.sforce.soap.metadata.FlowConnector faultConnector) {
      this.faultConnector = faultConnector;
      faultConnector__is_set = true;
    }

    /**
     * element : filters of type {http://soap.sforce.com/2006/04/metadata}FlowRecordFilter
     * java type: com.sforce.soap.metadata.FlowRecordFilter[]
     */
    private static final com.sforce.ws.bind.TypeInfo filters__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","filters","http://soap.sforce.com/2006/04/metadata","FlowRecordFilter",0,-1,true);

    private boolean filters__is_set = false;

    private com.sforce.soap.metadata.FlowRecordFilter[] filters = new com.sforce.soap.metadata.FlowRecordFilter[0];

    public com.sforce.soap.metadata.FlowRecordFilter[] getFilters() {
      return filters;
    }

    public void setFilters(com.sforce.soap.metadata.FlowRecordFilter[] filters) {
      this.filters = filters;
      filters__is_set = true;
    }

    /**
     * element : inputReference of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo inputReference__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","inputReference","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean inputReference__is_set = false;

    private java.lang.String inputReference;

    public java.lang.String getInputReference() {
      return inputReference;
    }

    public void setInputReference(java.lang.String inputReference) {
      this.inputReference = inputReference;
      inputReference__is_set = true;
    }

    /**
     * element : object of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo object__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","object","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean object__is_set = false;

    private java.lang.String object;

    public java.lang.String getObject() {
      return object;
    }

    public void setObject(java.lang.String object) {
      this.object = object;
      object__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "FlowRecordDelete");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, connector__typeInfo, connector, connector__is_set);
       __typeMapper.writeObject(__out, faultConnector__typeInfo, faultConnector, faultConnector__is_set);
       __typeMapper.writeObject(__out, filters__typeInfo, filters, filters__is_set);
       __typeMapper.writeString(__out, inputReference__typeInfo, inputReference, inputReference__is_set);
       __typeMapper.writeString(__out, object__typeInfo, object, object__is_set);
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
        if (__typeMapper.isElement(__in, connector__typeInfo)) {
            setConnector((com.sforce.soap.metadata.FlowConnector)__typeMapper.readObject(__in, connector__typeInfo, com.sforce.soap.metadata.FlowConnector.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, faultConnector__typeInfo)) {
            setFaultConnector((com.sforce.soap.metadata.FlowConnector)__typeMapper.readObject(__in, faultConnector__typeInfo, com.sforce.soap.metadata.FlowConnector.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, filters__typeInfo)) {
            setFilters((com.sforce.soap.metadata.FlowRecordFilter[])__typeMapper.readObject(__in, filters__typeInfo, com.sforce.soap.metadata.FlowRecordFilter[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, inputReference__typeInfo)) {
            setInputReference(__typeMapper.readString(__in, inputReference__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, object__typeInfo)) {
            setObject(__typeMapper.readString(__in, object__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FlowRecordDelete ");
      sb.append(super.toString());sb.append(" connector='").append(com.sforce.ws.util.Verbose.toString(connector)).append("'\n");
      sb.append(" faultConnector='").append(com.sforce.ws.util.Verbose.toString(faultConnector)).append("'\n");
      sb.append(" filters='").append(com.sforce.ws.util.Verbose.toString(filters)).append("'\n");
      sb.append(" inputReference='").append(com.sforce.ws.util.Verbose.toString(inputReference)).append("'\n");
      sb.append(" object='").append(com.sforce.ws.util.Verbose.toString(object)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
