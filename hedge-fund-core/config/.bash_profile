### SSH
# http://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ec2-key-pairs.html
# https://www.youtube.com/watch?v=SwMh5lSh_JM
alias sshCD="ssh -i ~/Documents/hedge-codedeploy.pem ec2-user@54.165.133.136"                
alias sshGW="ssh -i ~/Documents/hedge.pem ubuntu@54.86.144.36"                  

### AWS 
export AWS_CREDENTIAL_FILE=~/.aws/credentials

### JAVA
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
echo $JAVA_HOME
java -version
javac -version


### PROJECT
alias project="cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/"
project
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