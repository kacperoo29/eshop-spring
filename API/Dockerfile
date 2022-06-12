# App
FROM openjdk:11 as build

WORKDIR /app

# Download deps
COPY ["build.gradle", "gradlew", "settings.gradle", "./"]
ADD gradle ./gradle
RUN ./gradlew

# Build war
COPY . .
RUN ./gradlew war

# Payara
FROM payara/server-full:5.2020.6-jdk11
COPY --from=build /app/build/libs/eshop-0.0.1-SNAPSHOT-plain.war ./eshop.war

# Add postgresql plugin
ADD https://jdbc.postgresql.org/download/postgresql-42.4.0.jar .
RUN echo "add-library postgresql-42.4.0.jar" >> $PREBOOT_COMMANDS
USER root
RUN chmod 644 postgresql-42.4.0.jar
USER payara

# Deploy the WAR file to the server
RUN echo "deploy --name eshop --contextroot / eshop.war" >> $POSTBOOT_COMMANDS

# Create connection pool to postgres
RUN echo "create-jdbc-connection-pool --restype javax.sql.DataSource --datasourceclassname org.postgresql.ds.PGSimpleDataSource --property serverName=db:port=5432:databaseName=postgres:user=postgres:password=SY2Z74RzUx8Afs6d:useSSL=false eshop-pool" >> $POSTBOOT_COMMANDS

