package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class ReadMetadata_element implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public ReadMetadata_element() {}

    /**
     * element : type of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo type__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","type","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean type__is_set = false;

    private java.lang.String type;

    public java.lang.String getType() {
      return type;
    }

    public void setType(java.lang.String type) {
      this.type = type;
      type__is_set = true;
    }

    /**
     * element : fullNames of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo fullNames__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","fullNames","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean fullNames__is_set = false;

    private java.lang.String[] fullNames = new java.lang.String[0];

    public java.lang.String[] getFullNames() {
      return fullNames;
    }

    public void setFullNames(java.lang.String[] fullNames) {
      this.fullNames = fullNames;
      fullNames__is_set = true;
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
       __typeMapper.writeString(__out, type__typeInfo, type, type__is_set);
       __typeMapper.writeObject(__out, fullNames__typeInfo, fullNames, fullNames__is_set);
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
        if (__typeMapper.verifyElement(__in, type__typeInfo)) {
            setType(__typeMapper.readString(__in, type__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, fullNames__typeInfo)) {
            setFullNames((java.lang.String[])__typeMapper.readObject(__in, fullNames__typeInfo, java.lang.String[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[ReadMetadata_element ");
      sb.append(" type='").append(com.sforce.ws.util.Verbose.toString(type)).append("'\n");
      sb.append(" fullNames='").append(com.sforce.ws.util.Verbose.toString(fullNames)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
