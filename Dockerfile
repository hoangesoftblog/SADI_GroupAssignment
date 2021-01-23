FROM java:8-jdk-alpine

ARG JAR_FILE=target/*.jar
ARG INIT_DATA=data-12380.json

COPY ${JAR_FILE} app.jar
COPY data-12380.json data-12380.json
ENTRYPOINT ["java","-jar","app.jar"]