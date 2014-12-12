package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class QuickActionListItem implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public QuickActionListItem() {}

    /**
     * element : quickActionName of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo quickActionName__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","quickActionName","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean quickActionName__is_set = false;

    private java.lang.String quickActionName;

    public java.lang.String getQuickActionName() {
      return quickActionName;
    }

    public void setQuickActionName(java.lang.String quickActionName) {
      this.quickActionName = quickActionName;
      quickActionName__is_set = true;
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
       __typeMapper.writeString(__out, quickActionName__typeInfo, quickActionName, quickActionName__is_set);
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
        if (__typeMapper.verifyElement(__in, quickActionName__typeInfo)) {
            setQuickActionName(__typeMapper.readString(__in, quickActionName__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[QuickActionListItem ");
      sb.append(" quickActionName='").append(com.sforce.ws.util.Verbose.toString(quickActionName)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
