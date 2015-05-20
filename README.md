
## CI Setup (codeship)

    (cd hedge-fund-core/lib/aws-swf-tools && bash -x cmds.sh)
    mvn -N clean install
    (cd sforce-client-enterprise; mvn clean install)
    mvn -f hedge-fund-core/pom.xml clean package

