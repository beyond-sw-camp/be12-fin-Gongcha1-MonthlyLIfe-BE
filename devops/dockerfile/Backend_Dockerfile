FROM openjdk:17-jdk-slim
EXPOSE 8080

ADD ./build/libs/MonthlyLifeBackend-0.0.1-SNAPSHOT.jar /app.jar

#ENTRYPOINT['java' '-jar' '/app.jar']
#COPY ./build/resources/main/es-ca.crt /es-ca.crt

ENTRYPOINT ["sh", "-c", "if [ \"$DEBUG\" = \"true\" ]; then java -agentlib:jdwp=transport=dt_socket,server=n,address=host.docker.internal:5005,suspend=y -jar /app.jar; else java -jar /app.jar; fi"]