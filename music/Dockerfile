FROM openjdk:17-jdk-alpine
LABEL authors="vrtkarim"
ARG JAR-FILE=target/*.jar
COPY ./target/MusicTag-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

