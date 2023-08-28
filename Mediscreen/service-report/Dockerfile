FROM openjdk:11
EXPOSE 8083
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-report.jar
ENTRYPOINT ["java","-jar","/ms-report.jar"]