FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/live-coding-bonus-calculation-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT java -jar live-coding-bonus-calculation-service-0.0.1-SNAPSHOT.jar
