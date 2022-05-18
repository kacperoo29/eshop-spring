FROM openjdk:11-alpine

WORKDIR /app
COPY . .

RUN ./gradlew build

ENTRYPOINT [ "java", "-jar", "./build/libs/eshop-0.0.1-SNAPSHOT.jar" ]
