FROM openjdk:17-jdk-alpine
COPY target/*.jar vendix.jar
ENTRYPOINT ["java","-jar","/vendix.jar"]
EXPOSE 8080