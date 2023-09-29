FROM openjdk:20-ea-4-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/ecommerce-app-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]