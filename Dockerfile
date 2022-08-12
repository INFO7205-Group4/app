FROM openjdk:19-jdk-alpine3.16
MAINTAINER Group4
COPY target/boot-swagger-3-0.0.1-SNAPSHOT.jar boot-swagger-3-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/boot-swagger-3-0.0.1-SNAPSHOT.jar"]