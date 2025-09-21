FROM eclipse-temurin:21
ARG JAR_FILE="config-service/build/libs/config-service-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]