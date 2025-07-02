FROM maven:3.9.7-amazoncorretto-21
COPY . /appWorker
WORKDIR /appWorker
RUN mvn clean install package -DskipTests
CMD ["sh", "-c", "java -jar target/worker-1.jar"]