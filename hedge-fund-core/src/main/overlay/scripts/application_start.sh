#!/bin/bash -x

export set JAVA_OPTS="-Xms256m -Xmx1024m"

export SPARK_HOME=/opt/spark/
export PATH=$PATH:$SPARK_HOME/bin

export VERSION=${env.CI_COMMIT_ID}


printenv

for i in /etc/init/hedge-*.conf ; do

    FILE_NAME=`basename $i`
    SERVICE_NAME=${FILE_NAME%.conf}

    start $SERVICE_NAME || true
	
done

exit 0
