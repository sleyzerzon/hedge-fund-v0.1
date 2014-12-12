package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class AuraDefinitionBundle extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public AuraDefinitionBundle() {}

    /**
     * element : controllerContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo controllerContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","controllerContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean controllerContent__is_set = false;

    private byte[] controllerContent;

    public byte[] getControllerContent() {
      return controllerContent;
    }

    public void setControllerContent(byte[] controllerContent) {
      this.controllerContent = controllerContent;
      controllerContent__is_set = true;
    }

    /**
     * element : documentationContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo documentationContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","documentationContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean documentationContent__is_set = false;

    private byte[] documentationContent;

    public byte[] getDocumentationContent() {
      return documentationContent;
    }

    public void setDocumentationContent(byte[] documentationContent) {
      this.documentationContent = documentationContent;
      documentationContent__is_set = true;
    }

    /**
     * element : helperContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo helperContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","helperContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean helperContent__is_set = false;

    private byte[] helperContent;

    public byte[] getHelperContent() {
      return helperContent;
    }

    public void setHelperContent(byte[] helperContent) {
      this.helperContent = helperContent;
      helperContent__is_set = true;
    }

    /**
     * element : markup of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo markup__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","markup","http://www.w3.org/2001/XMLSchema","base64Binary",1,1,true);

    private boolean markup__is_set = false;

    private byte[] markup;

    public byte[] getMarkup() {
      return markup;
    }

    public void setMarkup(byte[] markup) {
      this.markup = markup;
      markup__is_set = true;
    }

    /**
     * element : modelContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo modelContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","modelContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean modelContent__is_set = false;

    private byte[] modelContent;

    public byte[] getModelContent() {
      return modelContent;
    }

    public void setModelContent(byte[] modelContent) {
      this.modelContent = modelContent;
      modelContent__is_set = true;
    }

    /**
     * element : rendererContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo rendererContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","rendererContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean rendererContent__is_set = false;

    private byte[] rendererContent;

    public byte[] getRendererContent() {
      return rendererContent;
    }

    public void setRendererContent(byte[] rendererContent) {
      this.rendererContent = rendererContent;
      rendererContent__is_set = true;
    }

    /**
     * element : styleContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo styleContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","styleContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean styleContent__is_set = false;

    private byte[] styleContent;

    public byte[] getStyleContent() {
      return styleContent;
    }

    public void setStyleContent(byte[] styleContent) {
      this.styleContent = styleContent;
      styleContent__is_set = true;
    }

    /**
     * element : testsuiteContent of type {http://www.w3.org/2001/XMLSchema}base64Binary
     * java type: byte[]
     */
    private static final com.sforce.ws.bind.TypeInfo testsuiteContent__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","testsuiteContent","http://www.w3.org/2001/XMLSchema","base64Binary",0,1,true);

    private boolean testsuiteContent__is_set = false;

    private byte[] testsuiteContent;

    public byte[] getTestsuiteContent() {
      return testsuiteContent;
    }

    public void setTestsuiteContent(byte[] testsuiteContent) {
      this.testsuiteContent = testsuiteContent;
      testsuiteContent__is_set = true;
    }

    /**
     * element : type of type {http://soap.sforce.com/2006/04/metadata}AuraBundleType
     * java type: com.sforce.soap.metadata.AuraBundleType
     */
    private static final com.sforce.ws.bind.TypeInfo type__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","type","http://soap.sforce.com/2006/04/metadata","AuraBundleType",1,1,true);

    private boolean type__is_set = false;

    private com.sforce.soap.metadata.AuraBundleType type;

    public com.sforce.soap.metadata.AuraBundleType getType() {
      return type;
    }

    public void setType(com.sforce.soap.metadata.AuraBundleType type) {
      this.type = type;
      type__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "AuraDefinitionBundle");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, controllerContent__typeInfo, controllerContent, controllerContent__is_set);
       __typeMapper.writeObject(__out, documentationContent__typeInfo, documentationContent, documentationContent__is_set);
       __typeMapper.writeObject(__out, helperContent__typeInfo, helperContent, helperContent__is_set);
       __typeMapper.writeObject(__out, markup__typeInfo, markup, markup__is_set);
       __typeMapper.writeObject(__out, modelContent__typeInfo, modelContent, modelContent__is_set);
       __typeMapper.writeObject(__out, rendererContent__typeInfo, rendererContent, rendererContent__is_set);
       __typeMapper.writeObject(__out, styleContent__typeInfo, styleContent, styleContent__is_set);
       __typeMapper.writeObject(__out, testsuiteContent__typeInfo, testsuiteContent, testsuiteContent__is_set);
       __typeMapper.writeObject(__out, type__typeInfo, type, type__is_set);
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
        if (__typeMapper.isElement(__in, controllerContent__typeInfo)) {
            setControllerContent((byte[])__typeMapper.readObject(__in, controllerContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, documentationContent__typeInfo)) {
            setDocumentationContent((byte[])__typeMapper.readObject(__in, documentationContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, helperContent__typeInfo)) {
            setHelperContent((byte[])__typeMapper.readObject(__in, helperContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, markup__typeInfo)) {
            setMarkup((byte[])__typeMapper.readObject(__in, markup__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, modelContent__typeInfo)) {
            setModelContent((byte[])__typeMapper.readObject(__in, modelContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, rendererContent__typeInfo)) {
            setRendererContent((byte[])__typeMapper.readObject(__in, rendererContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, styleContent__typeInfo)) {
            setStyleContent((byte[])__typeMapper.readObject(__in, styleContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, testsuiteContent__typeInfo)) {
            setTestsuiteContent((byte[])__typeMapper.readObject(__in, testsuiteContent__typeInfo, byte[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, type__typeInfo)) {
            setType((com.sforce.soap.metadata.AuraBundleType)__typeMapper.readObject(__in, type__typeInfo, com.sforce.soap.metadata.AuraBundleType.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[AuraDefinitionBundle ");
      sb.append(super.toString());sb.append(" controllerContent='").append(com.sforce.ws.util.Verbose.toString(controllerContent)).append("'\n");
      sb.append(" documentationContent='").append(com.sforce.ws.util.Verbose.toString(documentationContent)).append("'\n");
      sb.append(" helperContent='").append(com.sforce.ws.util.Verbose.toString(helperContent)).append("'\n");
      sb.append(" markup='").append(com.sforce.ws.util.Verbose.toString(markup)).append("'\n");
      sb.append(" modelContent='").append(com.sforce.ws.util.Verbose.toString(modelContent)).append("'\n");
      sb.append(" rendererContent='").append(com.sforce.ws.util.Verbose.toString(rendererContent)).append("'\n");
      sb.append(" styleContent='").append(com.sforce.ws.util.Verbose.toString(styleContent)).append("'\n");
      sb.append(" testsuiteContent='").append(com.sforce.ws.util.Verbose.toString(testsuiteContent)).append("'\n");
      sb.append(" type='").append(com.sforce.ws.util.Verbose.toString(type)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
