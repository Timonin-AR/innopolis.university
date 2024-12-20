FROM amazoncorretto:21-alpine-jdk
COPY src/main/resources/db-api.jar app.jar
COPY application.properties application.properties
ENTRYPOINT ["java","-jar","/app.jar"]
