### SSH 
# http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html
# https://www.youtube.com/watch?v=SwMh5lSh_JM
alias sshCD="ssh -i ~/Documents/hedge-codedeploy.pem ec2-user@54.165.133.136" 
alias sshGW="ssh -i ~/Documents/hedge.pem ubuntu@52.0.152.129" 

### CODEDEPLOY
# /hedge-fund-core/src/main/resources
# http://docs.aws.amazon.com/codedeploy/latest/userguide/writing-app-spec.html
# http://docs.aws.amazon.com/codedeploy/latest/userguide/how-to-add-appspec-file.html
# sudo su
service codedeploy-agent status

### AWS 
export AWS_CREDENTIAL_FILE=~/.aws/credentials

### JAVA
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
echo $JAVA_HOME
java -version
javac -version


### PROJECT
alias project="cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/"
alias logs="cd "/users/Shared/"
logs
export JARS=/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/
alias jhistorian="java -cp $JARS/hedge-fund-core-null.jar com.onenow.main.HistorianMain"
# jar tf hedge-fund-core-null.jar

### MAVEN 
export MAVEN_HOME=/Users/pablo/apache-maven-3.3.3
export PATH=$PATH:$MAVEN_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
mvn --version
alias build="mvn -N clean install"
alias package="mvn -Pdist -f hedge-fund-core/pom.xml clean package"
alias test="mvn test"

### SPARK
export SPARK_HOME=/Users/pablo/spark-1.3.1-bin-hadoop2.4
export PATH=$PATH:$SPARK_HOME/bin
alias sanalyst="spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar"
# pyspark

### GIT
# git rm --cached .folder/* -r 
# git add --all
# git commit -m "fixed gitignore"
# git push origin master

### KINESIS
# https://s3.amazonaws.com/kinesis-demo-bucket/amazon-kinesis-data-visualization-sample/kinesis-data-vis-sample-app.template
 



Pablos-MacBook-Air:~ pablo$ vi ~/.bash_profile

service codedeploy-agent status

### AWS
export AWS_CREDENTIAL_FILE=~/.aws/credentials

### JAVA
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
echo $JAVA_HOME
java -version
javac -version


### PROJECT
alias project="cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/"
alias logs="cd "/users/Shared/"
logs
export JARS=/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/
alias jhistorian="java -cp $JARS/hedge-fund-core-null.jar com.onenow.main.HistorianMain"
# jar tf hedge-fund-core-null.jar

### MAVEN
export MAVEN_HOME=/Users/pablo/apache-maven-3.3.3
export PATH=$PATH:$MAVEN_HOME/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
mvn --version
alias build="mvn -N clean install"
alias package="mvn -Pdist -f hedge-fund-core/pom.xml clean package"
alias test="mvn test"

### SPARK
export SPARK_HOME=/Users/pablo/spark-1.3.1-bin-hadoop2.4
export PATH=$PATH:$SPARK_HOME/bin
alias sanalyst="spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar"
# pyspark

### GIT
# git rm --cached .folder/* -r
# git add --all
# git commit -m "fixed gitignore"
# git push origin master

### KINESIS
# https://s3.amazonaws.com/kinesis-demo-bucket/amazon-kinesis-data-visualization-sample/kinesis-data-vis-sample-app.template

## CLOUDWATCH
# sudo su
# cd /root
# root@ip-172-31-36-250:~# sudo python ./awslogs-agent-setup.py --region us-east-1
# format: Jun 10, 2015 11:32:56 PM