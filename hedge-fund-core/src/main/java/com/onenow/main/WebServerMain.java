/*
 * Copyright 2014 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Amazon Software License (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 * http://aws.amazon.com/asl/
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.onenow.main;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.onenow.admin.InitAmazon;
import com.onenow.data.DynamoDBUtils;
import com.onenow.util.FlexibleLogger;
import com.onenow.util.InitLogger;
import com.onenow.util.SampleUtils;
import com.onenow.util.SysProperties;
import com.onenow.web.GetCountsServlet;

/**
 * Create an embedded HTTP server that responds with counts on the provided port.
 */
public class WebServerMain {
    /**
     * Start an embedded web server.
     * 
     * @param args Expecting 4 arguments: Port number, File path to static content, the name of the
     *        DynamoDB table where counts are persisted to, and the AWS region in which these resources
     *        exist or should be created.
     * @throws Exception Error starting the web server.
     */
    public static void main(String[] args) throws Exception {
    	
		InitLogger.run("");
    	
        if (args.length != 4) {
            System.err.println("Usage: " + WebServerMain.class
                    + " <port number> <directory for static content> <DynamoDB table name> <region>");
            System.exit(1);
        }
        Server server = new Server(Integer.parseInt(args[0]));
        String wwwroot = args[1];
        String countsTableName = args[2];
        Region region = SampleUtils.parseRegion(args[3]);

        // Servlet context
        ServletContextHandler context =
                new ServletContextHandler(ServletContextHandler.NO_SESSIONS | ServletContextHandler.NO_SECURITY);
        context.setContextPath("/api");

        // Static resource context
        ResourceHandler resources = new ResourceHandler();
        resources.setDirectoriesListed(false);
        resources.setWelcomeFiles(new String[] { "graph.html" });
        resources.setResourceBase(wwwroot);

        // Create the servlet to handle /GetCounts
        AWSCredentialsProvider credentialsProvider = new DefaultAWSCredentialsProviderChain();
        ClientConfiguration clientConfig = InitAmazon.getClientConfig();
        AmazonDynamoDB dynamoDB = new AmazonDynamoDBClient(credentialsProvider, clientConfig);
        dynamoDB.setRegion(region);
        DynamoDBUtils dynamoDBUtils = new DynamoDBUtils(dynamoDB);
        context.addServlet(new ServletHolder(new GetCountsServlet(dynamoDBUtils.createMapperForTable(countsTableName))),
                "/GetCounts/*");

        HandlerList handlers = new HandlerList();
        handlers.addHandler(context);
        handlers.addHandler(resources);
        handlers.addHandler(new DefaultHandler());

        server.setHandler(handlers);
        server.start();
        server.join();
    }
}
