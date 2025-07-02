FROM maven:3.9.7-amazoncorretto-21
COPY . /appUser
WORKDIR /appUser
RUN mvn clean install package -DskipTests
CMD ["sh", "-c", "java -jar target/user-1.jar"]