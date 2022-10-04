FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/Spring-demo-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT java -jar Spring-demo-0.0.1-SNAPSHOT.jar
