package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class DashboardMobileSettings implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public DashboardMobileSettings() {}

    /**
     * element : enableDashboardIPadApp of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo enableDashboardIPadApp__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","enableDashboardIPadApp","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean enableDashboardIPadApp__is_set = false;

    private boolean enableDashboardIPadApp;

    public boolean getEnableDashboardIPadApp() {
      return enableDashboardIPadApp;
    }

    public boolean isEnableDashboardIPadApp() {
      return enableDashboardIPadApp;
    }

    public void setEnableDashboardIPadApp(boolean enableDashboardIPadApp) {
      this.enableDashboardIPadApp = enableDashboardIPadApp;
      enableDashboardIPadApp__is_set = true;
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
       __typeMapper.writeBoolean(__out, enableDashboardIPadApp__typeInfo, enableDashboardIPadApp, enableDashboardIPadApp__is_set);
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
        if (__typeMapper.isElement(__in, enableDashboardIPadApp__typeInfo)) {
            setEnableDashboardIPadApp(__typeMapper.readBoolean(__in, enableDashboardIPadApp__typeInfo, boolean.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[DashboardMobileSettings ");
      sb.append(" enableDashboardIPadApp='").append(com.sforce.ws.util.Verbose.toString(enableDashboardIPadApp)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
