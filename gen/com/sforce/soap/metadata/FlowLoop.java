package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FlowLoop extends com.sforce.soap.metadata.FlowNode {

    /**
     * Constructor
     */
    public FlowLoop() {}

    /**
     * element : assignNextValueToReference of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo assignNextValueToReference__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","assignNextValueToReference","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean assignNextValueToReference__is_set = false;

    private java.lang.String assignNextValueToReference;

    public java.lang.String getAssignNextValueToReference() {
      return assignNextValueToReference;
    }

    public void setAssignNextValueToReference(java.lang.String assignNextValueToReference) {
      this.assignNextValueToReference = assignNextValueToReference;
      assignNextValueToReference__is_set = true;
    }

    /**
     * element : collectionReference of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo collectionReference__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","collectionReference","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean collectionReference__is_set = false;

    private java.lang.String collectionReference;

    public java.lang.String getCollectionReference() {
      return collectionReference;
    }

    public void setCollectionReference(java.lang.String collectionReference) {
      this.collectionReference = collectionReference;
      collectionReference__is_set = true;
    }

    /**
     * element : iterationOrder of type {http://soap.sforce.com/2006/04/metadata}IterationOrder
     * java type: com.sforce.soap.metadata.IterationOrder
     */
    private static final com.sforce.ws.bind.TypeInfo iterationOrder__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","iterationOrder","http://soap.sforce.com/2006/04/metadata","IterationOrder",0,1,true);

    private boolean iterationOrder__is_set = false;

    private com.sforce.soap.metadata.IterationOrder iterationOrder;

    public com.sforce.soap.metadata.IterationOrder getIterationOrder() {
      return iterationOrder;
    }

    public void setIterationOrder(com.sforce.soap.metadata.IterationOrder iterationOrder) {
      this.iterationOrder = iterationOrder;
      iterationOrder__is_set = true;
    }

    /**
     * element : nextValueConnector of type {http://soap.sforce.com/2006/04/metadata}FlowConnector
     * java type: com.sforce.soap.metadata.FlowConnector
     */
    private static final com.sforce.ws.bind.TypeInfo nextValueConnector__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","nextValueConnector","http://soap.sforce.com/2006/04/metadata","FlowConnector",0,1,true);

    private boolean nextValueConnector__is_set = false;

    private com.sforce.soap.metadata.FlowConnector nextValueConnector;

    public com.sforce.soap.metadata.FlowConnector getNextValueConnector() {
      return nextValueConnector;
    }

    public void setNextValueConnector(com.sforce.soap.metadata.FlowConnector nextValueConnector) {
      this.nextValueConnector = nextValueConnector;
      nextValueConnector__is_set = true;
    }

    /**
     * element : noMoreValuesConnector of type {http://soap.sforce.com/2006/04/metadata}FlowConnector
     * java type: com.sforce.soap.metadata.FlowConnector
     */
    private static final com.sforce.ws.bind.TypeInfo noMoreValuesConnector__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","noMoreValuesConnector","http://soap.sforce.com/2006/04/metadata","FlowConnector",0,1,true);

    private boolean noMoreValuesConnector__is_set = false;

    private com.sforce.soap.metadata.FlowConnector noMoreValuesConnector;

    public com.sforce.soap.metadata.FlowConnector getNoMoreValuesConnector() {
      return noMoreValuesConnector;
    }

    public void setNoMoreValuesConnector(com.sforce.soap.metadata.FlowConnector noMoreValuesConnector) {
      this.noMoreValuesConnector = noMoreValuesConnector;
      noMoreValuesConnector__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "FlowLoop");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeString(__out, assignNextValueToReference__typeInfo, assignNextValueToReference, assignNextValueToReference__is_set);
       __typeMapper.writeString(__out, collectionReference__typeInfo, collectionReference, collectionReference__is_set);
       __typeMapper.writeObject(__out, iterationOrder__typeInfo, iterationOrder, iterationOrder__is_set);
       __typeMapper.writeObject(__out, nextValueConnector__typeInfo, nextValueConnector, nextValueConnector__is_set);
       __typeMapper.writeObject(__out, noMoreValuesConnector__typeInfo, noMoreValuesConnector, noMoreValuesConnector__is_set);
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
        if (__typeMapper.verifyElement(__in, assignNextValueToReference__typeInfo)) {
            setAssignNextValueToReference(__typeMapper.readString(__in, assignNextValueToReference__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, collectionReference__typeInfo)) {
            setCollectionReference(__typeMapper.readString(__in, collectionReference__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, iterationOrder__typeInfo)) {
            setIterationOrder((com.sforce.soap.metadata.IterationOrder)__typeMapper.readObject(__in, iterationOrder__typeInfo, com.sforce.soap.metadata.IterationOrder.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, nextValueConnector__typeInfo)) {
            setNextValueConnector((com.sforce.soap.metadata.FlowConnector)__typeMapper.readObject(__in, nextValueConnector__typeInfo, com.sforce.soap.metadata.FlowConnector.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, noMoreValuesConnector__typeInfo)) {
            setNoMoreValuesConnector((com.sforce.soap.metadata.FlowConnector)__typeMapper.readObject(__in, noMoreValuesConnector__typeInfo, com.sforce.soap.metadata.FlowConnector.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FlowLoop ");
      sb.append(super.toString());sb.append(" assignNextValueToReference='").append(com.sforce.ws.util.Verbose.toString(assignNextValueToReference)).append("'\n");
      sb.append(" collectionReference='").append(com.sforce.ws.util.Verbose.toString(collectionReference)).append("'\n");
      sb.append(" iterationOrder='").append(com.sforce.ws.util.Verbose.toString(iterationOrder)).append("'\n");
      sb.append(" nextValueConnector='").append(com.sforce.ws.util.Verbose.toString(nextValueConnector)).append("'\n");
      sb.append(" noMoreValuesConnector='").append(com.sforce.ws.util.Verbose.toString(noMoreValuesConnector)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
