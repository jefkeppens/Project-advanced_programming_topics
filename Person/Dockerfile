FROM openjdk:17-jdk-alpine
EXPOSE 27017
ADD target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
