package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class ChannelLayout extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public ChannelLayout() {}

    /**
     * element : enabledChannels of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String[]
     */
    private static final com.sforce.ws.bind.TypeInfo enabledChannels__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enabledChannels","http://www.w3.org/2001/XMLSchema","string",0,-1,true);

    private boolean enabledChannels__is_set = false;

    private java.lang.String[] enabledChannels = new java.lang.String[0];

    public java.lang.String[] getEnabledChannels() {
      return enabledChannels;
    }

    public void setEnabledChannels(java.lang.String[] enabledChannels) {
      this.enabledChannels = enabledChannels;
      enabledChannels__is_set = true;
    }

    /**
     * element : label of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo label__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","label","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean label__is_set = false;

    private java.lang.String label;

    public java.lang.String getLabel() {
      return label;
    }

    public void setLabel(java.lang.String label) {
      this.label = label;
      label__is_set = true;
    }

    /**
     * element : layoutItems of type {http://soap.sforce.com/2006/04/metadata}ChannelLayoutItem
     * java type: com.sforce.soap.metadata.ChannelLayoutItem[]
     */
    private static final com.sforce.ws.bind.TypeInfo layoutItems__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","layoutItems","http://soap.sforce.com/2006/04/metadata","ChannelLayoutItem",0,-1,true);

    private boolean layoutItems__is_set = false;

    private com.sforce.soap.metadata.ChannelLayoutItem[] layoutItems = new com.sforce.soap.metadata.ChannelLayoutItem[0];

    public com.sforce.soap.metadata.ChannelLayoutItem[] getLayoutItems() {
      return layoutItems;
    }

    public void setLayoutItems(com.sforce.soap.metadata.ChannelLayoutItem[] layoutItems) {
      this.layoutItems = layoutItems;
      layoutItems__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "ChannelLayout");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, enabledChannels__typeInfo, enabledChannels, enabledChannels__is_set);
       __typeMapper.writeString(__out, label__typeInfo, label, label__is_set);
       __typeMapper.writeObject(__out, layoutItems__typeInfo, layoutItems, layoutItems__is_set);
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
        if (__typeMapper.isElement(__in, enabledChannels__typeInfo)) {
            setEnabledChannels((java.lang.String[])__typeMapper.readObject(__in, enabledChannels__typeInfo, java.lang.String[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, label__typeInfo)) {
            setLabel(__typeMapper.readString(__in, label__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, layoutItems__typeInfo)) {
            setLayoutItems((com.sforce.soap.metadata.ChannelLayoutItem[])__typeMapper.readObject(__in, layoutItems__typeInfo, com.sforce.soap.metadata.ChannelLayoutItem[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[ChannelLayout ");
      sb.append(super.toString());sb.append(" enabledChannels='").append(com.sforce.ws.util.Verbose.toString(enabledChannels)).append("'\n");
      sb.append(" label='").append(com.sforce.ws.util.Verbose.toString(label)).append("'\n");
      sb.append(" layoutItems='").append(com.sforce.ws.util.Verbose.toString(layoutItems)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
