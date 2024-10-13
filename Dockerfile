# syntax=docker/dockerfile:1
FROM openjdk:21-jdk
#RUN ./gradlew build
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

