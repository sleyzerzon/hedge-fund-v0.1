# HEDGE 
## Open Source Hedge Fund Platform
Contact: Pablo R. Bertorello (pablo.bertorello@gmail.com or Skype: pmrbertorello)

# CI Setup (CodeShip)

## Setup Commands

```
pip install --upgrade awscli
(cd hedge-fund-core/lib/aws-swf-tools && bash -x cmds.sh)
mvn -N clean install
(cd sforce-client-enterprise; mvn clean install)
```

## Test Commands

```
mvn -Pdist -f hedge-fund-core/pom.xml clean package
mvn test
```

## Deployment Commands

  * Deployment Method: Custom Script

```
VERSION=`date +%Y%m%d%H%M`-${CI_COMMIT_ID}
KEY=releases/hedge-fund-core/$VERSION/hedge-fund-core-dist-${CI_COMMIT_ID}.zip
TARGET=s3://onenow-releases/$KEY
aws s3 cp hedge-fund-core/target/hedge-fund-core-${CI_COMMIT_ID}-dist.zip $TARGET
aws deploy create-deployment --application-name hedge-fund --description $VERSION --s3-location bundleType=zip,bucket=onenow-releases,key=$KEY --deployment-group-name hedge-fund-deployment-group
```

## Environment Variables

Declare those:

  * AWS_ACCESS_KEY_ID
  * AWS_SECRET_ACCESS_KEY
  * AWS_DEFAULT_REGION=us-east-1

Ensure that the user has both S3 Put Access (s3:PutObject) as well as codedeploy access (codedeploy:*) on its policy.

```
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "Stmt1432064877000",
            "Effect": "Allow",
            "Action": [
                "s3:PutObject"
            ],
            "Resource": [
                "arn:aws:s3:::onenow-releases/*"
            ]
        },
        {
            "Sid": "codedeploy",
            "Effect": "Allow",
            "Action": [
                "codedeploy:*"
            ],
            "Resource": [ "*" ]
        }
    ]
}
```

# AWS Code Deploy Setup

Create a Code Deploy Application, and a Deployment Group consisting of your EC2 Machines. Then, run the codeship deploy.

When booting your EC2 Machines, ensure that they have the Agent installed. e.g., for Amazon Linux, you need to set the User Data Script as such:

    #!/bin/bash
    yum -y update
    yum install -y aws-cli
    cd /home/ec2-user
    aws s3 cp s3://aws-codedeploy-us-east-1/latest/install . --region us-east-1
    chmod +x ./install
    ./install auto

Other than that, ensure the EC2 Machine also belongs to a role able to access AWS.

# Services

Services are easy to maintain. Simply created hedge-*.conf files under hedge-fund-core/src/main/resources/upstart pointing to the right class.

    start on started networking
    
    respawn
    
    script
       VERSION=${env.CI_COMMIT_ID}
       java -cp /opt/hedge-fund/$VERSION/lib/hedge-fund-$VERSION.jar com.onenow.main.HistorianMain
    end script

Notice the appspec.yaml-referenced scripts look for hedge-fund-* in order to manage the job lifecycle.

