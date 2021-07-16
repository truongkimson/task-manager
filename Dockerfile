# Build stage
FROM maven:3.8.1-openjdk-16 AS build
WORKDIR /home/app
COPY src src
COPY pom.xml .
RUN mvn clean package

# Deploy stage
FROM openjdk:16
WORKDIR /home/app
COPY --from=build /home/app/target/task-manager-1.0-SNAPSHOT.jar task-manager.jar
COPY config.yml .
COPY scripts .
EXPOSE 8080
ENTRYPOINT ["./startup.sh"]
