# Sample project for Spring Data R2DBC with Azure Database for MySQL

This sample project is used in the [Use Spring Data R2DBC with Azure Database for MySQL](https://docs.microsoft.com/azure/developer/java/spring-framework/configure-spring-data-r2dbc-with-azure-mysql/?WT.mc_id=github-microsoftsamples-judubois) Microsoft documentation quickstart.

## Creating the infrastructure

We recommend you create an *env.sh* file to create the following environment variables:

```bash
#!/bin/sh

echo "Setting env variables"

export AZ_RESOURCE_GROUP=tmp-spring-r2dbc-mysql
export AZ_DATABASE_NAME=XXXXXX-tmp-spring-r2dbc-mysql
export AZ_LOCATION=eastus
export AZ_MYSQL_USERNAME=spring
export AZ_MYSQL_PASSWORD=XXXXXXXXXXXXXXXXXXX
export AZ_LOCAL_IP_ADDRESS=$(curl http://whatismyip.akamai.com/)

export SPRING_R2DBC_URL=r2dbc:pool:mysql://$AZ_DATABASE_NAME.mysql.database.azure.com:3306/demo
export SPRING_R2DBC_USERNAME=spring@$AZ_DATABASE_NAME
export SPRING_R2DBC_PASSWORD=$AZ_MYSQL_PASSWORD
```

You will need to set up a unique `AZ_DATABASE_NAME` as well as a correctly secured `AZ_MYSQL_PASSWORD`.

Once this file is created:

- Use `source env.sh` to set up those environment variables
- Use `./create-spring-data-r2dbc-mysql.sh` to create your infrastructure
- Use `./destroy-spring-data-r2dbc-mysql.sh` to delete your infrastructure

## Running the project

This is a standard Maven project, you can run it from your IDE, or using the provided Maven wrapper:

```bash
./mvnw spring-boot:run
```
