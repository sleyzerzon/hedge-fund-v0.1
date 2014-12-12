package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class MobileSettings extends com.sforce.soap.metadata.Metadata {

    /**
     * Constructor
     */
    public MobileSettings() {}

    /**
     * element : chatterMobile of type {http://soap.sforce.com/2006/04/metadata}ChatterMobileSettings
     * java type: com.sforce.soap.metadata.ChatterMobileSettings
     */
    private static final com.sforce.ws.bind.TypeInfo chatterMobile__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","chatterMobile","http://soap.sforce.com/2006/04/metadata","ChatterMobileSettings",0,1,true);

    private boolean chatterMobile__is_set = false;

    private com.sforce.soap.metadata.ChatterMobileSettings chatterMobile;

    public com.sforce.soap.metadata.ChatterMobileSettings getChatterMobile() {
      return chatterMobile;
    }

    public void setChatterMobile(com.sforce.soap.metadata.ChatterMobileSettings chatterMobile) {
      this.chatterMobile = chatterMobile;
      chatterMobile__is_set = true;
    }

    /**
     * element : dashboardMobile of type {http://soap.sforce.com/2006/04/metadata}DashboardMobileSettings
     * java type: com.sforce.soap.metadata.DashboardMobileSettings
     */
    private static final com.sforce.ws.bind.TypeInfo dashboardMobile__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","dashboardMobile","http://soap.sforce.com/2006/04/metadata","DashboardMobileSettings",0,1,true);

    private boolean dashboardMobile__is_set = false;

    private com.sforce.soap.metadata.DashboardMobileSettings dashboardMobile;

    public com.sforce.soap.metadata.DashboardMobileSettings getDashboardMobile() {
      return dashboardMobile;
    }

    public void setDashboardMobile(com.sforce.soap.metadata.DashboardMobileSettings dashboardMobile) {
      this.dashboardMobile = dashboardMobile;
      dashboardMobile__is_set = true;
    }

    /**
     * element : salesforceMobile of type {http://soap.sforce.com/2006/04/metadata}SFDCMobileSettings
     * java type: com.sforce.soap.metadata.SFDCMobileSettings
     */
    private static final com.sforce.ws.bind.TypeInfo salesforceMobile__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","salesforceMobile","http://soap.sforce.com/2006/04/metadata","SFDCMobileSettings",0,1,true);

    private boolean salesforceMobile__is_set = false;

    private com.sforce.soap.metadata.SFDCMobileSettings salesforceMobile;

    public com.sforce.soap.metadata.SFDCMobileSettings getSalesforceMobile() {
      return salesforceMobile;
    }

    public void setSalesforceMobile(com.sforce.soap.metadata.SFDCMobileSettings salesforceMobile) {
      this.salesforceMobile = salesforceMobile;
      salesforceMobile__is_set = true;
    }

    /**
     * element : touchMobile of type {http://soap.sforce.com/2006/04/metadata}TouchMobileSettings
     * java type: com.sforce.soap.metadata.TouchMobileSettings
     */
    private static final com.sforce.ws.bind.TypeInfo touchMobile__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","touchMobile","http://soap.sforce.com/2006/04/metadata","TouchMobileSettings",0,1,true);

    private boolean touchMobile__is_set = false;

    private com.sforce.soap.metadata.TouchMobileSettings touchMobile;

    public com.sforce.soap.metadata.TouchMobileSettings getTouchMobile() {
      return touchMobile;
    }

    public void setTouchMobile(com.sforce.soap.metadata.TouchMobileSettings touchMobile) {
      this.touchMobile = touchMobile;
      touchMobile__is_set = true;
    }

    /**
     */
    @Override
    public void write(javax.xml.namespace.QName __element,
        com.sforce.ws.parser.XmlOutputStream __out, com.sforce.ws.bind.TypeMapper __typeMapper)
        throws java.io.IOException {
      __out.writeStartTag(__element.getNamespaceURI(), __element.getLocalPart());
      __typeMapper.writeXsiType(__out, "http://soap.sforce.com/2006/04/metadata", "MobileSettings");
      writeFields(__out, __typeMapper);
      __out.writeEndTag(__element.getNamespaceURI(), __element.getLocalPart());
    }

    protected void writeFields(com.sforce.ws.parser.XmlOutputStream __out,
         com.sforce.ws.bind.TypeMapper __typeMapper)
         throws java.io.IOException {
       super.writeFields(__out, __typeMapper);
       __typeMapper.writeObject(__out, chatterMobile__typeInfo, chatterMobile, chatterMobile__is_set);
       __typeMapper.writeObject(__out, dashboardMobile__typeInfo, dashboardMobile, dashboardMobile__is_set);
       __typeMapper.writeObject(__out, salesforceMobile__typeInfo, salesforceMobile, salesforceMobile__is_set);
       __typeMapper.writeObject(__out, touchMobile__typeInfo, touchMobile, touchMobile__is_set);
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
        if (__typeMapper.isElement(__in, chatterMobile__typeInfo)) {
            setChatterMobile((com.sforce.soap.metadata.ChatterMobileSettings)__typeMapper.readObject(__in, chatterMobile__typeInfo, com.sforce.soap.metadata.ChatterMobileSettings.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, dashboardMobile__typeInfo)) {
            setDashboardMobile((com.sforce.soap.metadata.DashboardMobileSettings)__typeMapper.readObject(__in, dashboardMobile__typeInfo, com.sforce.soap.metadata.DashboardMobileSettings.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, salesforceMobile__typeInfo)) {
            setSalesforceMobile((com.sforce.soap.metadata.SFDCMobileSettings)__typeMapper.readObject(__in, salesforceMobile__typeInfo, com.sforce.soap.metadata.SFDCMobileSettings.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, touchMobile__typeInfo)) {
            setTouchMobile((com.sforce.soap.metadata.TouchMobileSettings)__typeMapper.readObject(__in, touchMobile__typeInfo, com.sforce.soap.metadata.TouchMobileSettings.class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[MobileSettings ");
      sb.append(super.toString());sb.append(" chatterMobile='").append(com.sforce.ws.util.Verbose.toString(chatterMobile)).append("'\n");
      sb.append(" dashboardMobile='").append(com.sforce.ws.util.Verbose.toString(dashboardMobile)).append("'\n");
      sb.append(" salesforceMobile='").append(com.sforce.ws.util.Verbose.toString(salesforceMobile)).append("'\n");
      sb.append(" touchMobile='").append(com.sforce.ws.util.Verbose.toString(touchMobile)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
