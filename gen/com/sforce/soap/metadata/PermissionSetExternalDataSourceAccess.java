package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class PermissionSetExternalDataSourceAccess implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public PermissionSetExternalDataSourceAccess() {}

    /**
     * element : enabled of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enabled__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enabled","http://www.w3.org/2001/XMLSchema","boolean",1,1,true);

    private boolean enabled__is_set = false;

    private boolean enabled;

    public boolean getEnabled() {
      return enabled;
    }

    public boolean isEnabled() {
      return enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
      enabled__is_set = true;
    }

    /**
     * element : externalDataSource of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo externalDataSource__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","externalDataSource","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean externalDataSource__is_set = false;

    private java.lang.String externalDataSource;

    public java.lang.String getExternalDataSource() {
      return externalDataSource;
    }

    public void setExternalDataSource(java.lang.String externalDataSource) {
      this.externalDataSource = externalDataSource;
      externalDataSource__is_set = true;
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
       __typeMapper.writeBoolean(__out, enabled__typeInfo, enabled, enabled__is_set);
       __typeMapper.writeString(__out, externalDataSource__typeInfo, externalDataSource, externalDataSource__is_set);
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
        if (__typeMapper.verifyElement(__in, enabled__typeInfo)) {
            setEnabled(__typeMapper.readBoolean(__in, enabled__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, externalDataSource__typeInfo)) {
            setExternalDataSource(__typeMapper.readString(__in, externalDataSource__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[PermissionSetExternalDataSourceAccess ");
      sb.append(" enabled='").append(com.sforce.ws.util.Verbose.toString(enabled)).append("'\n");
      sb.append(" externalDataSource='").append(com.sforce.ws.util.Verbose.toString(externalDataSource)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
