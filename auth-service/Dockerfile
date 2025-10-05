FROM eclipse-temurin:21
ARG JAR_FILE="auth-service/build/libs/auth-service.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]