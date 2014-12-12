package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class ConnectedAppCanvasConfig implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public ConnectedAppCanvasConfig() {}

    /**
     * element : accessMethod of type {http://soap.sforce.com/2006/04/metadata}AccessMethod
     * java type: com.sforce.soap.metadata.AccessMethod
     */
    private static final com.sforce.ws.bind.TypeInfo accessMethod__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","accessMethod","http://soap.sforce.com/2006/04/metadata","AccessMethod",1,1,true);

    private boolean accessMethod__is_set = false;

    private com.sforce.soap.metadata.AccessMethod accessMethod;

    public com.sforce.soap.metadata.AccessMethod getAccessMethod() {
      return accessMethod;
    }

    public void setAccessMethod(com.sforce.soap.metadata.AccessMethod accessMethod) {
      this.accessMethod = accessMethod;
      accessMethod__is_set = true;
    }

    /**
     * element : canvasUrl of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo canvasUrl__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","canvasUrl","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean canvasUrl__is_set = false;

    private java.lang.String canvasUrl;

    public java.lang.String getCanvasUrl() {
      return canvasUrl;
    }

    public void setCanvasUrl(java.lang.String canvasUrl) {
      this.canvasUrl = canvasUrl;
      canvasUrl__is_set = true;
    }

    /**
     * element : lifecycleClass of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo lifecycleClass__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","lifecycleClass","http://www.w3.org/2001/XMLSchema","string",0,1,true);

    private boolean lifecycleClass__is_set = false;

    private java.lang.String lifecycleClass;

    public java.lang.String getLifecycleClass() {
      return lifecycleClass;
    }

    public void setLifecycleClass(java.lang.String lifecycleClass) {
      this.lifecycleClass = lifecycleClass;
      lifecycleClass__is_set = true;
    }

    /**
     * element : locations of type {http://soap.sforce.com/2006/04/metadata}CanvasLocationOptions
     * java type: com.sforce.soap.metadata.CanvasLocationOptions[]
     */
    private static final com.sforce.ws.bind.TypeInfo locations__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","locations","http://soap.sforce.com/2006/04/metadata","CanvasLocationOptions",0,-1,true);

    private boolean locations__is_set = false;

    private com.sforce.soap.metadata.CanvasLocationOptions[] locations = new com.sforce.soap.metadata.CanvasLocationOptions[0];

    public com.sforce.soap.metadata.CanvasLocationOptions[] getLocations() {
      return locations;
    }

    public void setLocations(com.sforce.soap.metadata.CanvasLocationOptions[] locations) {
      this.locations = locations;
      locations__is_set = true;
    }

    /**
     * element : options of type {http://soap.sforce.com/2006/04/metadata}CanvasOptions
     * java type: com.sforce.soap.metadata.CanvasOptions[]
     */
    private static final com.sforce.ws.bind.TypeInfo options__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","options","http://soap.sforce.com/2006/04/metadata","CanvasOptions",0,-1,true);

    private boolean options__is_set = false;

    private com.sforce.soap.metadata.CanvasOptions[] options = new com.sforce.soap.metadata.CanvasOptions[0];

    public com.sforce.soap.metadata.CanvasOptions[] getOptions() {
      return options;
    }

    public void setOptions(com.sforce.soap.metadata.CanvasOptions[] options) {
      this.options = options;
      options__is_set = true;
    }

    /**
     * element : samlInitiationMethod of type {http://soap.sforce.com/2006/04/metadata}SamlInitiationMethod
     * java type: com.sforce.soap.metadata.SamlInitiationMethod
     */
    private static final com.sforce.ws.bind.TypeInfo samlInitiationMethod__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","samlInitiationMethod","http://soap.sforce.com/2006/04/metadata","SamlInitiationMethod",0,1,true);

    private boolean samlInitiationMethod__is_set = false;

    private com.sforce.soap.metadata.SamlInitiationMethod samlInitiationMethod;

    public com.sforce.soap.metadata.SamlInitiationMethod getSamlInitiationMethod() {
      return samlInitiationMethod;
    }

    public void setSamlInitiationMethod(com.sforce.soap.metadata.SamlInitiationMethod samlInitiationMethod) {
      this.samlInitiationMethod = samlInitiationMethod;
      samlInitiationMethod__is_set = true;
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
       __typeMapper.writeObject(__out, accessMethod__typeInfo, accessMethod, accessMethod__is_set);
       __typeMapper.writeString(__out, canvasUrl__typeInfo, canvasUrl, canvasUrl__is_set);
       __typeMapper.writeString(__out, lifecycleClass__typeInfo, lifecycleClass, lifecycleClass__is_set);
       __typeMapper.writeObject(__out, locations__typeInfo, locations, locations__is_set);
       __typeMapper.writeObject(__out, options__typeInfo, options, options__is_set);
       __typeMapper.writeObject(__out, samlInitiationMethod__typeInfo, samlInitiationMethod, samlInitiationMethod__is_set);
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
        if (__typeMapper.verifyElement(__in, accessMethod__typeInfo)) {
            setAccessMethod((com.sforce.soap.metadata.AccessMethod)__typeMapper.readObject(__in, accessMethod__typeInfo, com.sforce.soap.metadata.AccessMethod.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, canvasUrl__typeInfo)) {
            setCanvasUrl(__typeMapper.readString(__in, canvasUrl__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, lifecycleClass__typeInfo)) {
            setLifecycleClass(__typeMapper.readString(__in, lifecycleClass__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, locations__typeInfo)) {
            setLocations((com.sforce.soap.metadata.CanvasLocationOptions[])__typeMapper.readObject(__in, locations__typeInfo, com.sforce.soap.metadata.CanvasLocationOptions[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, options__typeInfo)) {
            setOptions((com.sforce.soap.metadata.CanvasOptions[])__typeMapper.readObject(__in, options__typeInfo, com.sforce.soap.metadata.CanvasOptions[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, samlInitiationMethod__typeInfo)) {
            setSamlInitiationMethod((com.sforce.soap.metadata.SamlInitiationMethod)__typeMapper.readObject(__in, samlInitiationMethod__typeInfo, com.sforce.soap.metadata.SamlInitiationMethod.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[ConnectedAppCanvasConfig ");
      sb.append(" accessMethod='").append(com.sforce.ws.util.Verbose.toString(accessMethod)).append("'\n");
      sb.append(" canvasUrl='").append(com.sforce.ws.util.Verbose.toString(canvasUrl)).append("'\n");
      sb.append(" lifecycleClass='").append(com.sforce.ws.util.Verbose.toString(lifecycleClass)).append("'\n");
      sb.append(" locations='").append(com.sforce.ws.util.Verbose.toString(locations)).append("'\n");
      sb.append(" options='").append(com.sforce.ws.util.Verbose.toString(options)).append("'\n");
      sb.append(" samlInitiationMethod='").append(com.sforce.ws.util.Verbose.toString(samlInitiationMethod)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
