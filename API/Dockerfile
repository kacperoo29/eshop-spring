FROM openjdk:11

WORKDIR /app

COPY ["build.gradle", "gradlew", "settings.gradle", "./"]
ADD gradle ./gradle
RUN ./gradlew

COPY . .
RUN ./gradlew build

ENTRYPOINT [ "java", "-jar", "./build/libs/eshop-0.0.1-SNAPSHOT.jar" ]
