#FROM ubuntu:latest
#LABEL authors="jotsh"
#
#ENTRYPOINT ["top", "-b"]
#

# Specify the layers of an image
FROM openjdk:24-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/IDDSI-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]



#FROM openjdk:24-jdk
## eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
#COPY target/*.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

