#!/bin/bash -x

printenv

for i in /etc/init/hedge-*.conf ; do
    FILE_NAME=`basename $i`
    SERVICE_NAME=${FILE_NAME%.conf}
    
    stop $SERVICE_NAME || true
done
