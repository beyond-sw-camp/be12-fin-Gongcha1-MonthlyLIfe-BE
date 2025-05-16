FROM openjdk:17-jdk-slim
EXPOSE 8080

ADD ./batch/build/libs/batch-0.0.1-SNAPSHOT.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
