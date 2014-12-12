package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class LiveChatButtonSkills implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public LiveChatButtonSkills() {}

    /**
     * element : skill of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo skill__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","skill","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean skill__is_set = false;

    private java.lang.String[] skill = new java.lang.String[0];

    public java.lang.String[] getSkill() {
      return skill;
    }

    public void setSkill(java.lang.String[] skill) {
      this.skill = skill;
      skill__is_set = true;
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
       __typeMapper.writeObject(__out, skill__typeInfo, skill, skill__is_set);
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
        if (__typeMapper.isElement(__in, skill__typeInfo)) {
            setSkill((java.lang.String[])__typeMapper.readObject(__in, skill__typeInfo, java.lang.String[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[LiveChatButtonSkills ");
      sb.append(" skill='").append(com.sforce.ws.util.Verbose.toString(skill)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
