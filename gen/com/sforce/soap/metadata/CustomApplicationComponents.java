package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class CustomApplicationComponents implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public CustomApplicationComponents() {}

    /**
     * element : alignment of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo alignment__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","alignment","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean alignment__is_set = false;

    private java.lang.String alignment;

    public java.lang.String getAlignment() {
      return alignment;
    }

    public void setAlignment(java.lang.String alignment) {
      this.alignment = alignment;
      alignment__is_set = true;
    }

    /**
     * element : customApplicationComponent of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo customApplicationComponent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","customApplicationComponent","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean customApplicationComponent__is_set = false;

    private java.lang.String[] customApplicationComponent = new java.lang.String[0];

    public java.lang.String[] getCustomApplicationComponent() {
      return customApplicationComponent;
    }

    public void setCustomApplicationComponent(java.lang.String[] customApplicationComponent) {
      this.customApplicationComponent = customApplicationComponent;
      customApplicationComponent__is_set = true;
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
       __typeMapper.writeString(__out, alignment__typeInfo, alignment, alignment__is_set);
       __typeMapper.writeObject(__out, customApplicationComponent__typeInfo, customApplicationComponent, customApplicationComponent__is_set);
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
        if (__typeMapper.verifyElement(__in, alignment__typeInfo)) {
            setAlignment(__typeMapper.readString(__in, alignment__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, customApplicationComponent__typeInfo)) {
            setCustomApplicationComponent((java.lang.String[])__typeMapper.readObject(__in, customApplicationComponent__typeInfo, java.lang.String[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[CustomApplicationComponents ");
      sb.append(" alignment='").append(com.sforce.ws.util.Verbose.toString(alignment)).append("'\n");
      sb.append(" customApplicationComponent='").append(com.sforce.ws.util.Verbose.toString(customApplicationComponent)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
