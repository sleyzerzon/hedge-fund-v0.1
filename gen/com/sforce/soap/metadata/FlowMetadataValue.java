package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FlowMetadataValue implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public FlowMetadataValue() {}

    /**
     * element : name of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo name__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","name","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean name__is_set = false;

    private java.lang.String name;

    public java.lang.String getName() {
      return name;
    }

    public void setName(java.lang.String name) {
      this.name = name;
      name__is_set = true;
    }

    /**
     * element : value of type {http://soap.sforce.com/2006/04/metadata}FlowElementReferenceOrValue
     * java type: com.sforce.soap.metadata.FlowElementReferenceOrValue
     */
    private static final com.sforce.ws.bind.TypeInfo value__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","value","http://soap.sforce.com/2006/04/metadata","FlowElementReferenceOrValue",0,1,true);

    private boolean value__is_set = false;

    private com.sforce.soap.metadata.FlowElementReferenceOrValue value;

    public com.sforce.soap.metadata.FlowElementReferenceOrValue getValue() {
      return value;
    }

    public void setValue(com.sforce.soap.metadata.FlowElementReferenceOrValue value) {
      this.value = value;
      value__is_set = true;
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
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeObject(__out, value__typeInfo, value, value__is_set);
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
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, value__typeInfo)) {
            setValue((com.sforce.soap.metadata.FlowElementReferenceOrValue)__typeMapper.readObject(__in, value__typeInfo, com.sforce.soap.metadata.FlowElementReferenceOrValue.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FlowMetadataValue ");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" value='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
