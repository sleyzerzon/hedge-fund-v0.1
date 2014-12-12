package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FlowBaseElement implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public FlowBaseElement() {}

    /**
     * element : processMetadataValues of type {http://soap.sforce.com/2006/04/metadata}FlowMetadataValue
     * java type: com.sforce.soap.metadata.FlowMetadataValue[]
     */
    private static final com.sforce.ws.bind.TypeInfo processMetadataValues__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","processMetadataValues","http://soap.sforce.com/2006/04/metadata","FlowMetadataValue",0,-1,true);

    private boolean processMetadataValues__is_set = false;

    private com.sforce.soap.metadata.FlowMetadataValue[] processMetadataValues = new com.sforce.soap.metadata.FlowMetadataValue[0];

    public com.sforce.soap.metadata.FlowMetadataValue[] getProcessMetadataValues() {
      return processMetadataValues;
    }

    public void setProcessMetadataValues(com.sforce.soap.metadata.FlowMetadataValue[] processMetadataValues) {
      this.processMetadataValues = processMetadataValues;
      processMetadataValues__is_set = true;
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
       __typeMapper.writeObject(__out, processMetadataValues__typeInfo, processMetadataValues, processMetadataValues__is_set);
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
        if (__typeMapper.isElement(__in, processMetadataValues__typeInfo)) {
            setProcessMetadataValues((com.sforce.soap.metadata.FlowMetadataValue[])__typeMapper.readObject(__in, processMetadataValues__typeInfo, com.sforce.soap.metadata.FlowMetadataValue[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FlowBaseElement ");
      sb.append(" processMetadataValues='").append(com.sforce.ws.util.Verbose.toString(processMetadataValues)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
