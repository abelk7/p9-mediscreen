FROM openjdk:11
EXPOSE 8081
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} ms-patient.jar
ENTRYPOINT ["java","-jar","/ms-patient.jar"]