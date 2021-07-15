# Build stage
FROM maven:3.8.1-jdk-11 AS build
WORKDIR /home/app
COPY src src
COPY pom.xml .
RUN mvn clean package

# Deploy stage
FROM openjdk:11-jre-slim
WORKDIR /home/app
COPY --from=build /home/app/target/task-manager-1.0-SNAPSHOT.jar task-manager.jar
COPY config.yml .
EXPOSE 8080
RUN java -jar task-manager.jar db migrate config.yml
ENTRYPOINT ["java", "-jar", "task-manager.jar", "server", "config.yml"]
