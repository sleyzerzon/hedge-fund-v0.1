package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FlowChoice extends com.sforce.soap.metadata.FlowElement {

    /**
     * Constructor
     */
    public FlowChoice() {}

    /**
     * element : choiceText of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo choiceText__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","choiceText","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean choiceText__is_set = false;

    private java.lang.String choiceText;

    public java.lang.String getChoiceText() {
      return choiceText;
    }

    public void setChoiceText(java.lang.String choiceText) {
      this.choiceText = choiceText;
      choiceText__is_set = true;
    }

    /**
     * element : dataType of type {http://soap.sforce.com/2006/04/metadata}FlowDataType
     * java type: com.sforce.soap.metadata.FlowDataType
     */
    private static final com.sforce.ws.bind.TypeInfo dataType__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","dataType","http://soap.sforce.com/2006/04/metadata","FlowDataType",1,1,true);

    private boolean dataType__is_set = false;

    private com.sforce.soap.metadata.FlowDataType dataType;

    public com.sforce.soap.metadata.FlowDataType getDataType() {
      return dataType;
    }

    public void setDataType(com.sforce.soap.metadata.FlowDataType dataType) {
      this.dataType = dataType;
      dataType__is_set = true;
    }

    /**
     * element : userInput of type {http://soap.sforce.com/2006/04/metadata}FlowChoiceUserInput
     * java type: com.sforce.soap.metadata.FlowChoiceUserInput
     */
    private static final com.sforce.ws.bind.TypeInfo userInput__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","userInput","http://soap.sforce.com/2006/04/metadata","FlowChoiceUserInput",0,1,true);

    private boolean userInput__is_set = false;

    private com.sforce.soap.metadata.FlowChoiceUserInput userInput;

    public com.sforce.soap.metadata.FlowChoiceUserInput getUserInput() {
      return userInput;
    }

    public void setUserInput(com.sforce.soap.metadata.FlowChoiceUserInput userInput) {
      this.userInput = userInput;
      userInput__is_set = true;
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
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "FlowChoice");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeString(__out, choiceText__typeInfo, choiceText, choiceText__is_set);
       __typeMapper.writeObject(__out, dataType__typeInfo, dataType, dataType__is_set);
       __typeMapper.writeObject(__out, userInput__typeInfo, userInput, userInput__is_set);
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
        super.loadFields(__in, __typeMapper);
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, choiceText__typeInfo)) {
            setChoiceText(__typeMapper.readString(__in, choiceText__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, dataType__typeInfo)) {
            setDataType((com.sforce.soap.metadata.FlowDataType)__typeMapper.readObject(__in, dataType__typeInfo, com.sforce.soap.metadata.FlowDataType.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, userInput__typeInfo)) {
            setUserInput((com.sforce.soap.metadata.FlowChoiceUserInput)__typeMapper.readObject(__in, userInput__typeInfo, com.sforce.soap.metadata.FlowChoiceUserInput.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, value__typeInfo)) {
            setValue((com.sforce.soap.metadata.FlowElementReferenceOrValue)__typeMapper.readObject(__in, value__typeInfo, com.sforce.soap.metadata.FlowElementReferenceOrValue.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FlowChoice ");
      sb.append(super.toString());sb.append(" choiceText='").append(com.sforce.ws.util.Verbose.toString(choiceText)).append("'\n");
      sb.append(" dataType='").append(com.sforce.ws.util.Verbose.toString(dataType)).append("'\n");
      sb.append(" userInput='").append(com.sforce.ws.util.Verbose.toString(userInput)).append("'\n");
      sb.append(" value='").append(com.sforce.ws.util.Verbose.toString(value)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
