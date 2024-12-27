FROM maven:3.9.7-amazoncorretto-21
COPY . /app
WORKDIR /app
RUN mvn clean install
ENTRYPOINT ["java","-jar","target/lesson9-1.jar"]
