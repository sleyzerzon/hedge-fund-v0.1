#!/bin/bash -x

export set JAVA_OPTS="-Xms256m -Xmx1024m"

printenv

for i in /etc/init/hedge-*.conf ; do

    FILE_NAME=`basename $i`
    SERVICE_NAME=${FILE_NAME%.conf}

    start $SERVICE_NAME || true
	
    sudo service awslogs restart

done

exit 0
