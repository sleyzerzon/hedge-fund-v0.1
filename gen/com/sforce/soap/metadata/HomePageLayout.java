package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class HomePageLayout extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public HomePageLayout() {}

    /**
     * element : narrowComponents of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo narrowComponents__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","narrowComponents","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean narrowComponents__is_set = false;

    private java.lang.String[] narrowComponents = new java.lang.String[0];

    public java.lang.String[] getNarrowComponents() {
      return narrowComponents;
    }

    public void setNarrowComponents(java.lang.String[] narrowComponents) {
      this.narrowComponents = narrowComponents;
      narrowComponents__is_set = true;
    }

    /**
     * element : wideComponents of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo wideComponents__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","wideComponents","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean wideComponents__is_set = false;

    private java.lang.String[] wideComponents = new java.lang.String[0];

    public java.lang.String[] getWideComponents() {
      return wideComponents;
    }

    public void setWideComponents(java.lang.String[] wideComponents) {
      this.wideComponents = wideComponents;
      wideComponents__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "HomePageLayout");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, narrowComponents__typeInfo, narrowComponents, narrowComponents__is_set);
       __typeMapper.writeObject(__out, wideComponents__typeInfo, wideComponents, wideComponents__is_set);
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
        if (__typeMapper.isElement(__in, narrowComponents__typeInfo)) {
            setNarrowComponents((java.lang.String[])__typeMapper.readObject(__in, narrowComponents__typeInfo, java.lang.String[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, wideComponents__typeInfo)) {
            setWideComponents((java.lang.String[])__typeMapper.readObject(__in, wideComponents__typeInfo, java.lang.String[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[HomePageLayout ");
      sb.append(super.toString());sb.append(" narrowComponents='").append(com.sforce.ws.util.Verbose.toString(narrowComponents)).append("'\n");
      sb.append(" wideComponents='").append(com.sforce.ws.util.Verbose.toString(wideComponents)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
