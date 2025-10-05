FROM eclipse-temurin:21
ARG JAR_FILE="discovery-service/build/libs/discovery-service.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]