package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class CodeCoverageResult implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public CodeCoverageResult() {}

    /**
     * element : dmlInfo of type {http://soap.sforce.com/2006/04/metadata}CodeLocation
     * java type: com.sforce.soap.metadata.CodeLocation[]
     */
    private static final com.sforce.ws.bind.TypeInfo dmlInfo__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","dmlInfo","http://soap.sforce.com/2006/04/metadata","CodeLocation",0,-1,true);

    private boolean dmlInfo__is_set = false;

    private com.sforce.soap.metadata.CodeLocation[] dmlInfo = new com.sforce.soap.metadata.CodeLocation[0];

    public com.sforce.soap.metadata.CodeLocation[] getDmlInfo() {
      return dmlInfo;
    }

    public void setDmlInfo(com.sforce.soap.metadata.CodeLocation[] dmlInfo) {
      this.dmlInfo = dmlInfo;
      dmlInfo__is_set = true;
    }

    /**
     * element : id of type {http://soap.sforce.com/2006/04/metadata}ID
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo id__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","id","http://soap.sforce.com/2006/04/metadata","ID",1,1,true);

    private boolean id__is_set = false;

    private java.lang.String id;

    public java.lang.String getId() {
      return id;
    }

    public void setId(java.lang.String id) {
      this.id = id;
      id__is_set = true;
    }

    /**
     * element : locationsNotCovered of type {http://soap.sforce.com/2006/04/metadata}CodeLocation
     * java type: com.sforce.soap.metadata.CodeLocation[]
     */
    private static final com.sforce.ws.bind.TypeInfo locationsNotCovered__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","locationsNotCovered","http://soap.sforce.com/2006/04/metadata","CodeLocation",0,-1,true);

    private boolean locationsNotCovered__is_set = false;

    private com.sforce.soap.metadata.CodeLocation[] locationsNotCovered = new com.sforce.soap.metadata.CodeLocation[0];

    public com.sforce.soap.metadata.CodeLocation[] getLocationsNotCovered() {
      return locationsNotCovered;
    }

    public void setLocationsNotCovered(com.sforce.soap.metadata.CodeLocation[] locationsNotCovered) {
      this.locationsNotCovered = locationsNotCovered;
      locationsNotCovered__is_set = true;
    }

    /**
     * element : methodInfo of type {http://soap.sforce.com/2006/04/metadata}CodeLocation
     * java type: com.sforce.soap.metadata.CodeLocation[]
     */
    private static final com.sforce.ws.bind.TypeInfo methodInfo__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","methodInfo","http://soap.sforce.com/2006/04/metadata","CodeLocation",0,-1,true);

    private boolean methodInfo__is_set = false;

    private com.sforce.soap.metadata.CodeLocation[] methodInfo = new com.sforce.soap.metadata.CodeLocation[0];

    public com.sforce.soap.metadata.CodeLocation[] getMethodInfo() {
      return methodInfo;
    }

    public void setMethodInfo(com.sforce.soap.metadata.CodeLocation[] methodInfo) {
      this.methodInfo = methodInfo;
      methodInfo__is_set = true;
    }

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
     * element : namespace of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo namespace__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","namespace","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean namespace__is_set = false;

    private java.lang.String namespace;

    public java.lang.String getNamespace() {
      return namespace;
    }

    public void setNamespace(java.lang.String namespace) {
      this.namespace = namespace;
      namespace__is_set = true;
    }

    /**
     * element : numLocations of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo numLocations__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","numLocations","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean numLocations__is_set = false;

    private int numLocations;

    public int getNumLocations() {
      return numLocations;
    }

    public void setNumLocations(int numLocations) {
      this.numLocations = numLocations;
      numLocations__is_set = true;
    }

    /**
     * element : numLocationsNotCovered of type {http://www.w3.org/2001/XMLSchema}int
     * java type: int
     */
    private static final com.sforce.ws.bind.TypeInfo numLocationsNotCovered__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","numLocationsNotCovered","http://www.w3.org/2001/XMLSchema","int",1,1,true);

    private boolean numLocationsNotCovered__is_set = false;

    private int numLocationsNotCovered;

    public int getNumLocationsNotCovered() {
      return numLocationsNotCovered;
    }

    public void setNumLocationsNotCovered(int numLocationsNotCovered) {
      this.numLocationsNotCovered = numLocationsNotCovered;
      numLocationsNotCovered__is_set = true;
    }

    /**
     * element : soqlInfo of type {http://soap.sforce.com/2006/04/metadata}CodeLocation
     * java type: com.sforce.soap.metadata.CodeLocation[]
     */
    private static final com.sforce.ws.bind.TypeInfo soqlInfo__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","soqlInfo","http://soap.sforce.com/2006/04/metadata","CodeLocation",0,-1,true);

    private boolean soqlInfo__is_set = false;

    private com.sforce.soap.metadata.CodeLocation[] soqlInfo = new com.sforce.soap.metadata.CodeLocation[0];

    public com.sforce.soap.metadata.CodeLocation[] getSoqlInfo() {
      return soqlInfo;
    }

    public void setSoqlInfo(com.sforce.soap.metadata.CodeLocation[] soqlInfo) {
      this.soqlInfo = soqlInfo;
      soqlInfo__is_set = true;
    }

    /**
     * element : soslInfo of type {http://soap.sforce.com/2006/04/metadata}CodeLocation
     * java type: com.sforce.soap.metadata.CodeLocation[]
     */
    private static final com.sforce.ws.bind.TypeInfo soslInfo__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","soslInfo","http://soap.sforce.com/2006/04/metadata","CodeLocation",0,-1,true);

    private boolean soslInfo__is_set = false;

    private com.sforce.soap.metadata.CodeLocation[] soslInfo = new com.sforce.soap.metadata.CodeLocation[0];

    public com.sforce.soap.metadata.CodeLocation[] getSoslInfo() {
      return soslInfo;
    }

    public void setSoslInfo(com.sforce.soap.metadata.CodeLocation[] soslInfo) {
      this.soslInfo = soslInfo;
      soslInfo__is_set = true;
    }

    /**
     * element : type of type {http://www.w3.org/2001/XMLSchema}string
     * java type: java.lang.String
     */
    private static final com.sforce.ws.bind.TypeInfo type__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","type","http://www.w3.org/2001/XMLSchema","string",1,1,true);

    private boolean type__is_set = false;

    private java.lang.String type;

    public java.lang.String getType() {
      return type;
    }

    public void setType(java.lang.String type) {
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
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       __typeMapper.writeObject(__out, dmlInfo__typeInfo, dmlInfo, dmlInfo__is_set);
       __typeMapper.writeString(__out, id__typeInfo, id, id__is_set);
       __typeMapper.writeObject(__out, locationsNotCovered__typeInfo, locationsNotCovered, locationsNotCovered__is_set);
       __typeMapper.writeObject(__out, methodInfo__typeInfo, methodInfo, methodInfo__is_set);
       __typeMapper.writeString(__out, name__typeInfo, name, name__is_set);
       __typeMapper.writeString(__out, namespace__typeInfo, namespace, namespace__is_set);
       __typeMapper.writeInt(__out, numLocations__typeInfo, numLocations, numLocations__is_set);
       __typeMapper.writeInt(__out, numLocationsNotCovered__typeInfo, numLocationsNotCovered, numLocationsNotCovered__is_set);
       __typeMapper.writeObject(__out, soqlInfo__typeInfo, soqlInfo, soqlInfo__is_set);
       __typeMapper.writeObject(__out, soslInfo__typeInfo, soslInfo, soslInfo__is_set);
       __typeMapper.writeString(__out, type__typeInfo, type, type__is_set);
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
        if (__typeMapper.isElement(__in, dmlInfo__typeInfo)) {
            setDmlInfo((com.sforce.soap.metadata.CodeLocation[])__typeMapper.readObject(__in, dmlInfo__typeInfo, com.sforce.soap.metadata.CodeLocation[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, id__typeInfo)) {
            setId(__typeMapper.readString(__in, id__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, locationsNotCovered__typeInfo)) {
            setLocationsNotCovered((com.sforce.soap.metadata.CodeLocation[])__typeMapper.readObject(__in, locationsNotCovered__typeInfo, com.sforce.soap.metadata.CodeLocation[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, methodInfo__typeInfo)) {
            setMethodInfo((com.sforce.soap.metadata.CodeLocation[])__typeMapper.readObject(__in, methodInfo__typeInfo, com.sforce.soap.metadata.CodeLocation[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, name__typeInfo)) {
            setName(__typeMapper.readString(__in, name__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, namespace__typeInfo)) {
            setNamespace(__typeMapper.readString(__in, namespace__typeInfo, java.lang.String.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, numLocations__typeInfo)) {
            setNumLocations((int)__typeMapper.readInt(__in, numLocations__typeInfo, int.class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, numLocationsNotCovered__typeInfo)) {
            setNumLocationsNotCovered((int)__typeMapper.readInt(__in, numLocationsNotCovered__typeInfo, int.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, soqlInfo__typeInfo)) {
            setSoqlInfo((com.sforce.soap.metadata.CodeLocation[])__typeMapper.readObject(__in, soqlInfo__typeInfo, com.sforce.soap.metadata.CodeLocation[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, soslInfo__typeInfo)) {
            setSoslInfo((com.sforce.soap.metadata.CodeLocation[])__typeMapper.readObject(__in, soslInfo__typeInfo, com.sforce.soap.metadata.CodeLocation[].class));
        }
        __in.peekTag();
        if (__typeMapper.verifyElement(__in, type__typeInfo)) {
            setType(__typeMapper.readString(__in, type__typeInfo, java.lang.String.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[CodeCoverageResult ");
      sb.append(" dmlInfo='").append(com.sforce.ws.util.Verbose.toString(dmlInfo)).append("'\n");
      sb.append(" id='").append(com.sforce.ws.util.Verbose.toString(id)).append("'\n");
      sb.append(" locationsNotCovered='").append(com.sforce.ws.util.Verbose.toString(locationsNotCovered)).append("'\n");
      sb.append(" methodInfo='").append(com.sforce.ws.util.Verbose.toString(methodInfo)).append("'\n");
      sb.append(" name='").append(com.sforce.ws.util.Verbose.toString(name)).append("'\n");
      sb.append(" namespace='").append(com.sforce.ws.util.Verbose.toString(namespace)).append("'\n");
      sb.append(" numLocations='").append(com.sforce.ws.util.Verbose.toString(numLocations)).append("'\n");
      sb.append(" numLocationsNotCovered='").append(com.sforce.ws.util.Verbose.toString(numLocationsNotCovered)).append("'\n");
      sb.append(" soqlInfo='").append(com.sforce.ws.util.Verbose.toString(soqlInfo)).append("'\n");
      sb.append(" soslInfo='").append(com.sforce.ws.util.Verbose.toString(soslInfo)).append("'\n");
      sb.append(" type='").append(com.sforce.ws.util.Verbose.toString(type)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
