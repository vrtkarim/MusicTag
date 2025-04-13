FROM openjdk:17-jdk-alpine
LABEL authors="vrtkarim"
ARG JAR-FILE=target/*.jar
COPY ./target/musictag.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

