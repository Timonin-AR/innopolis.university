FROM maven:3.9.7-amazoncorretto-21
COPY . /appNote
WORKDIR /appNote
RUN mvn clean install package -DskipTests
CMD ["sh", "-c", "java -jar target/note-1.jar"]

