Last login: Thu May 28 08:57:55 on ttys002
/Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home
java version "1.8.0_40"
Java(TM) SE Runtime Environment (build 1.8.0_40-b27)
Java HotSpot(TM) 64-Bit Server VM (build 25.40-b25, mixed mode)
javac 1.8.0_40
Apache Maven 3.3.3 (7994120775791599e205a5524ec3e0dfe41d4a06; 2015-04-22T04:57:37-07:00)
Maven home: /Users/pablo/apache-maven-3.3.3
Java version: 1.8.0_40, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_40.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.10.3", arch: "x86_64", family: "mac"
Pablos-MacBook-Air:hedge-fund-parent pablo$ ls
README.md			hedge-fund-demo			sforce-client-enterprise
hedge-fund-core			pom.xml
Pablos-MacBook-Air:hedge-fund-parent pablo$ pwd
/Users/pablo/Documents/EclipseWorkspaceMaven/hedge-fund-parent
Pablos-MacBook-Air:hedge-fund-parent pablo$ cd 
Pablos-MacBook-Air:~ pablo$ vi Documents/PabloSSL.pem.txt 
Pablos-MacBook-Air:~ pablo$ vi .bash_profile 











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

### GIT
# git rm --cached .folder/* -r
# git add --all
# git commit -m "fixed gitignore"
# git push origin master



".bash_profile" 56L, 1656C written

