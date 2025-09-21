FROM eclipse-temurin:21
ARG JAR_FILE="gateway-service/build/libs/gateway-service-0.0.1-SNAPSHOT.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]