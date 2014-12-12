package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class InstalledPackage extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public InstalledPackage() {}

    /**
     * element : password of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo password__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","password","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean password__is_set = false;

    private java.lang.String password;

    public java.lang.String getPassword() {
      return password;
    }

    public void setPassword(java.lang.String password) {
      this.password = password;
      password__is_set = true;
    }

    /**
     * element : versionNumber of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo versionNumber__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","versionNumber","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean versionNumber__is_set = false;

    private java.lang.String versionNumber;

    public java.lang.String getVersionNumber() {
      return versionNumber;
    }

    public void setVersionNumber(java.lang.String versionNumber) {
      this.versionNumber = versionNumber;
      versionNumber__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "InstalledPackage");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeString(__out, password__typeInfo, password, password__is_set);
       __typeMapper.writeString(__out, versionNumber__typeInfo, versionNumber, versionNumber__is_set);
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
        if (__typeMapper.isElement(__in, password__typeInfo)) {
            setPassword(__typeMapper.readString(__in, password__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, versionNumber__typeInfo)) {
            setVersionNumber(__typeMapper.readString(__in, versionNumber__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[InstalledPackage ");
      sb.append(super.toString());sb.append(" password='").append(com.sforce.ws.util.Verbose.toString(password)).append("'\n");
      sb.append(" versionNumber='").append(com.sforce.ws.util.Verbose.toString(versionNumber)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
