FROM openjdk:11
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-client.jar
ENTRYPOINT ["java","-jar","/ms-client.jar"]