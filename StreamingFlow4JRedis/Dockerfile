FROM adoptopenjdk/openjdk11-openj9:alpine-slim

RUN mkdir -p /app

VOLUME /app

ENV JAVA_OPTS="-Xms64m -Xmx128m -XX:MaxPermSize=128m"
ENV JAR_NAME="spring-boot-0.0.1-SNAPSHOT.jar"

COPY ./build/libs/${JAR_NAME} /app

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/${JAR_NAME}" ]