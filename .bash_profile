### JAVA
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
echo $JAVA_HOME
java -version
javac -version


### PROJECT
cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/
export JARS=/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/hedge-fund-core/target/
alias historian="java -cp $JARS/hedge-fund-core-null.jar com.onenow.main.HistorianMain"
# jar tf hedge-fund-core-null.jar

### MAVEN
export PATH=$PATH:/Users/pablo/apache-maven-3.3.3/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
mvn --version
alias build="mvn -N clean install"
alias package="mvn -Pdist -f hedge-fund-core/pom.xml clean package"
alias test="mvn test"

### SPARK
export PATH=$PATH:/Users/pablo/spark-1.3.1-bin-hadoop2.4/bin
alias submit="spark-submit --class com.onenow.main.AnalystMain $JARS/hedge-fund-core-null.jar"
# pyspark
