### JAVA
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
echo $JAVA_HOME
java -version
javac -version


### PROJECT
alias parent="cd /Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent/"
alias historian="java -cp hedge-fund-core/target/hedge-fund-core-null.jar com.onenow.main.HistorianMain"

### MAVEN
export PATH=$PATH:/Users/pablo/apache-maven-3.3.3/bin
export MAVEN_OPTS="-Xms256m -Xmx512m"
mvn --version
alias build="mvn -N clean install"
alias package="mvn -Pdist -f hedge-fund-core/pom.xml clean package"
alias test="mvn test"
