FROM openjdk:11
EXPOSE 8082
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-notes.jar
ENTRYPOINT ["java","-jar","/ms-notes.jar"]