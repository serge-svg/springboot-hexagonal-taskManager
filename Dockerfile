FROM openjdk:11-ea-17-jre-slim

EXPOSE 8080

COPY ./build/libs/task-manager-1.0-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app

ENTRYPOINT ["java", "-jar", "task-manager-1.0-SNAPSHOT.jar"]