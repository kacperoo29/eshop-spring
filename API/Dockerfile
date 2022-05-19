FROM openjdk:11

WORKDIR /app
COPY . .

RUN ./gradlew --info build

ENTRYPOINT [ "java", "-jar", "./build/libs/eshop-0.0.1-SNAPSHOT.jar" ]
