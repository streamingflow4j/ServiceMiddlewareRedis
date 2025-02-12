FROM openjdk:21-jdk-slim

RUN mkdir -p /app

VOLUME /app

ENV JAVA_OPTS="-Xms64m -Xmx128m -XX:MaxMetaspaceSize=128m --add-opens java.base/java.lang=ALL-UNNAMED"
ENV JAR_NAME="streamingflow4j-0.0.1-SNAPSHOT.jar"

COPY ./build/libs/${JAR_NAME} /app

EXPOSE 6379

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app/${JAR_NAME}" ]
