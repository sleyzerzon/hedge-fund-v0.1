package com.sforce.soap.metadata;

/**
 * Generated by ComplexTypeCodeGenerator.java. Please do not edit.
 */
public class FeedLayout implements com.sforce.ws.bind.XMLizable {

    /**
     * Constructor
     */
    public FeedLayout() {}

    /**
     * element : autocollapsePublisher of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo autocollapsePublisher__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","autocollapsePublisher","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean autocollapsePublisher__is_set = false;

    private boolean autocollapsePublisher;

    public boolean getAutocollapsePublisher() {
      return autocollapsePublisher;
    }

    public boolean isAutocollapsePublisher() {
      return autocollapsePublisher;
    }

    public void setAutocollapsePublisher(boolean autocollapsePublisher) {
      this.autocollapsePublisher = autocollapsePublisher;
      autocollapsePublisher__is_set = true;
    }

    /**
     * element : compactFeed of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo compactFeed__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","compactFeed","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean compactFeed__is_set = false;

    private boolean compactFeed;

    public boolean getCompactFeed() {
      return compactFeed;
    }

    public boolean isCompactFeed() {
      return compactFeed;
    }

    public void setCompactFeed(boolean compactFeed) {
      this.compactFeed = compactFeed;
      compactFeed__is_set = true;
    }

    /**
     * element : feedFilterPosition of type {http://soap.sforce.com/2006/04/metadata}FeedLayoutFilterPosition
     * java type: com.sforce.soap.metadata.FeedLayoutFilterPosition
     */
    private static final com.sforce.ws.bind.TypeInfo feedFilterPosition__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","feedFilterPosition","http://soap.sforce.com/2006/04/metadata","FeedLayoutFilterPosition",0,1,true);

    private boolean feedFilterPosition__is_set = false;

    private com.sforce.soap.metadata.FeedLayoutFilterPosition feedFilterPosition;

    public com.sforce.soap.metadata.FeedLayoutFilterPosition getFeedFilterPosition() {
      return feedFilterPosition;
    }

    public void setFeedFilterPosition(com.sforce.soap.metadata.FeedLayoutFilterPosition feedFilterPosition) {
      this.feedFilterPosition = feedFilterPosition;
      feedFilterPosition__is_set = true;
    }

    /**
     * element : feedFilters of type {http://soap.sforce.com/2006/04/metadata}FeedLayoutFilter
     * java type: com.sforce.soap.metadata.FeedLayoutFilter[]
     */
    private static final com.sforce.ws.bind.TypeInfo feedFilters__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","feedFilters","http://soap.sforce.com/2006/04/metadata","FeedLayoutFilter",0,-1,true);

    private boolean feedFilters__is_set = false;

    private com.sforce.soap.metadata.FeedLayoutFilter[] feedFilters = new com.sforce.soap.metadata.FeedLayoutFilter[0];

    public com.sforce.soap.metadata.FeedLayoutFilter[] getFeedFilters() {
      return feedFilters;
    }

    public void setFeedFilters(com.sforce.soap.metadata.FeedLayoutFilter[] feedFilters) {
      this.feedFilters = feedFilters;
      feedFilters__is_set = true;
    }

    /**
     * element : fullWidthFeed of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo fullWidthFeed__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","fullWidthFeed","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean fullWidthFeed__is_set = false;

    private boolean fullWidthFeed;

    public boolean getFullWidthFeed() {
      return fullWidthFeed;
    }

    public boolean isFullWidthFeed() {
      return fullWidthFeed;
    }

    public void setFullWidthFeed(boolean fullWidthFeed) {
      this.fullWidthFeed = fullWidthFeed;
      fullWidthFeed__is_set = true;
    }

    /**
     * element : hideSidebar of type {http://www.w3.org/2001/XMLSchema}boolean
     * java type: boolean
     */
    private static final com.sforce.ws.bind.TypeInfo hideSidebar__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","hideSidebar","http://www.w3.org/2001/XMLSchema","boolean",0,1,true);

    private boolean hideSidebar__is_set = false;

    private boolean hideSidebar;

    public boolean getHideSidebar() {
      return hideSidebar;
    }

    public boolean isHideSidebar() {
      return hideSidebar;
    }

    public void setHideSidebar(boolean hideSidebar) {
      this.hideSidebar = hideSidebar;
      hideSidebar__is_set = true;
    }

    /**
     * element : leftComponents of type {http://soap.sforce.com/2006/04/metadata}FeedLayoutComponent
     * java type: com.sforce.soap.metadata.FeedLayoutComponent[]
     */
    private static final com.sforce.ws.bind.TypeInfo leftComponents__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","leftComponents","http://soap.sforce.com/2006/04/metadata","FeedLayoutComponent",0,-1,true);

    private boolean leftComponents__is_set = false;

    private com.sforce.soap.metadata.FeedLayoutComponent[] leftComponents = new com.sforce.soap.metadata.FeedLayoutComponent[0];

    public com.sforce.soap.metadata.FeedLayoutComponent[] getLeftComponents() {
      return leftComponents;
    }

    public void setLeftComponents(com.sforce.soap.metadata.FeedLayoutComponent[] leftComponents) {
      this.leftComponents = leftComponents;
      leftComponents__is_set = true;
    }

    /**
     * element : rightComponents of type {http://soap.sforce.com/2006/04/metadata}FeedLayoutComponent
     * java type: com.sforce.soap.metadata.FeedLayoutComponent[]
     */
    private static final com.sforce.ws.bind.TypeInfo rightComponents__typeInfo =
      new com.sforce.ws.bind.TypeInfo("http://soap.sforce.com/2006/04/metadata","rightComponents","http://soap.sforce.com/2006/04/metadata","FeedLayoutComponent",0,-1,true);

    private boolean rightComponents__is_set = false;

    private com.sforce.soap.metadata.FeedLayoutComponent[] rightComponents = new com.sforce.soap.metadata.FeedLayoutComponent[0];

    public com.sforce.soap.metadata.FeedLayoutComponent[] getRightComponents() {
      return rightComponents;
    }

    public void setRightComponents(com.sforce.soap.metadata.FeedLayoutComponent[] rightComponents) {
      this.rightComponents = rightComponents;
      rightComponents__is_set = true;
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
       __typeMapper.writeBoolean(__out, autocollapsePublisher__typeInfo, autocollapsePublisher, autocollapsePublisher__is_set);
       __typeMapper.writeBoolean(__out, compactFeed__typeInfo, compactFeed, compactFeed__is_set);
       __typeMapper.writeObject(__out, feedFilterPosition__typeInfo, feedFilterPosition, feedFilterPosition__is_set);
       __typeMapper.writeObject(__out, feedFilters__typeInfo, feedFilters, feedFilters__is_set);
       __typeMapper.writeBoolean(__out, fullWidthFeed__typeInfo, fullWidthFeed, fullWidthFeed__is_set);
       __typeMapper.writeBoolean(__out, hideSidebar__typeInfo, hideSidebar, hideSidebar__is_set);
       __typeMapper.writeObject(__out, leftComponents__typeInfo, leftComponents, leftComponents__is_set);
       __typeMapper.writeObject(__out, rightComponents__typeInfo, rightComponents, rightComponents__is_set);
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
        if (__typeMapper.isElement(__in, autocollapsePublisher__typeInfo)) {
            setAutocollapsePublisher(__typeMapper.readBoolean(__in, autocollapsePublisher__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, compactFeed__typeInfo)) {
            setCompactFeed(__typeMapper.readBoolean(__in, compactFeed__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, feedFilterPosition__typeInfo)) {
            setFeedFilterPosition((com.sforce.soap.metadata.FeedLayoutFilterPosition)__typeMapper.readObject(__in, feedFilterPosition__typeInfo, com.sforce.soap.metadata.FeedLayoutFilterPosition.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, feedFilters__typeInfo)) {
            setFeedFilters((com.sforce.soap.metadata.FeedLayoutFilter[])__typeMapper.readObject(__in, feedFilters__typeInfo, com.sforce.soap.metadata.FeedLayoutFilter[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, fullWidthFeed__typeInfo)) {
            setFullWidthFeed(__typeMapper.readBoolean(__in, fullWidthFeed__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, hideSidebar__typeInfo)) {
            setHideSidebar(__typeMapper.readBoolean(__in, hideSidebar__typeInfo, boolean.class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, leftComponents__typeInfo)) {
            setLeftComponents((com.sforce.soap.metadata.FeedLayoutComponent[])__typeMapper.readObject(__in, leftComponents__typeInfo, com.sforce.soap.metadata.FeedLayoutComponent[].class));
        }
        __in.peekTag();
        if (__typeMapper.isElement(__in, rightComponents__typeInfo)) {
            setRightComponents((com.sforce.soap.metadata.FeedLayoutComponent[])__typeMapper.readObject(__in, rightComponents__typeInfo, com.sforce.soap.metadata.FeedLayoutComponent[].class));
        }
    }

    @Override
    public String toString() {
      java.lang.StringBuilder sb = new java.lang.StringBuilder();
      sb.append("[FeedLayout ");
      sb.append(" autocollapsePublisher='").append(com.sforce.ws.util.Verbose.toString(autocollapsePublisher)).append("'\n");
      sb.append(" compactFeed='").append(com.sforce.ws.util.Verbose.toString(compactFeed)).append("'\n");
      sb.append(" feedFilterPosition='").append(com.sforce.ws.util.Verbose.toString(feedFilterPosition)).append("'\n");
      sb.append(" feedFilters='").append(com.sforce.ws.util.Verbose.toString(feedFilters)).append("'\n");
      sb.append(" fullWidthFeed='").append(com.sforce.ws.util.Verbose.toString(fullWidthFeed)).append("'\n");
      sb.append(" hideSidebar='").append(com.sforce.ws.util.Verbose.toString(hideSidebar)).append("'\n");
      sb.append(" leftComponents='").append(com.sforce.ws.util.Verbose.toString(leftComponents)).append("'\n");
      sb.append(" rightComponents='").append(com.sforce.ws.util.Verbose.toString(rightComponents)).append("'\n");
      sb.append("]\n");
      return sb.toString();
    }

}
