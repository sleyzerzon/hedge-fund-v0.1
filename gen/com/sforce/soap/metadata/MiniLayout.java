package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class MiniLayout implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public MiniLayout() {}

    /**
     * element : fields of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo fields__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","fields","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean fields__is_set = false;

    private java.lang.String[] fields = new java.lang.String[0];

    public java.lang.String[] getFields() {
      return fields;
    }

    public void setFields(java.lang.String[] fields) {
      this.fields = fields;
      fields__is_set = true;
    }

    /**
     * element : relatedLists of type {http://soap.sforce.com/2006/04/metadata}RelatedListItem
     * java type: com.sforce.soap.metadata.RelatedListItem[]
     */
    private static final com.sforce.ws.bind.TypeInfo relatedLists__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","relatedLists","http://soap.sforce.com/2006/04/metadata","RelatedListItem",0,-1,true);

    private boolean relatedLists__is_set = false;

    private com.sforce.soap.metadata.RelatedListItem[] relatedLists = new com.sforce.soap.metadata.RelatedListItem[0];

    public com.sforce.soap.metadata.RelatedListItem[] getRelatedLists() {
      return relatedLists;
    }

    public void setRelatedLists(com.sforce.soap.metadata.RelatedListItem[] relatedLists) {
      this.relatedLists = relatedLists;
      relatedLists__is_set = true;
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
       __typeMapper.writeObject(__out, fields__typeInfo, fields, fields__is_set);
       __typeMapper.writeObject(__out, relatedLists__typeInfo, relatedLists, relatedLists__is_set);
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
        if (__typeMapper.isElement(__in, fields__typeInfo)) {
            setFields((java.lang.String[])__typeMapper.readObject(__in, fields__typeInfo, java.lang.String[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, relatedLists__typeInfo)) {
            setRelatedLists((com.sforce.soap.metadata.RelatedListItem[])__typeMapper.readObject(__in, relatedLists__typeInfo, com.sforce.soap.metadata.RelatedListItem[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[MiniLayout ");
      sb.append(" fields='").append(com.sforce.ws.util.Verbose.toString(fields)).append("'\n");
      sb.append(" relatedLists='").append(com.sforce.ws.util.Verbose.toString(relatedLists)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
