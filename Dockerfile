FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY target/bonus-demo-0.0.1-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT java -jar bonus-demo-0.0.1-SNAPSHOT.jar
