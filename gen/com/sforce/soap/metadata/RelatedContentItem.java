package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class RelatedContentItem implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public RelatedContentItem() {}

    /**
     * element : layoutItem of type {http://soap.sforce.com/2006/04/metadata}LayoutItem
     * java type: com.sforce.soap.metadata.LayoutItem
     */
    private static final com.sforce.ws.bind.TypeInfo layoutItem__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","layoutItem","http://soap.sforce.com/2006/04/metadata","LayoutItem",1,1,true);

    private boolean layoutItem__is_set = false;

    private com.sforce.soap.metadata.LayoutItem layoutItem;

    public com.sforce.soap.metadata.LayoutItem getLayoutItem() {
      return layoutItem;
    }

    public void setLayoutItem(com.sforce.soap.metadata.LayoutItem layoutItem) {
      this.layoutItem = layoutItem;
      layoutItem__is_set = true;
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
       __typeMapper.writeObject(__out, layoutItem__typeInfo, layoutItem, layoutItem__is_set);
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
        if (__typeMapper.verifyElement(__in, layoutItem__typeInfo)) {
            setLayoutItem((com.sforce.soap.metadata.LayoutItem)__typeMapper.readObject(__in, layoutItem__typeInfo, com.sforce.soap.metadata.LayoutItem.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[RelatedContentItem ");
      sb.append(" layoutItem='").append(com.sforce.ws.util.Verbose.toString(layoutItem)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
