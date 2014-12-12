#!/bin/sh
java -cp /Users/admin/.m2/repository/com/force/api/force-wsc/33.0.0/force-wsc-33.0.0.jar:/Users/admin/.m2/repository/org/antlr/ST4/4.0.7/ST4-4.0.7.jar:/Users/admin/.m2/repository/org/antlr/antlr-runtime/3.5/antlr-runtime-3.5.jar com.sforce.ws.tools.wsdlc $1 $2
