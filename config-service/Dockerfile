FROM eclipse-temurin:21
# Создаем папку .ssh внутри контейнера
RUN mkdir /root/.ssh && chmod 700 /root/.ssh

# Копируем приватный ключ внутрь контейнера
COPY ./id_rsa /root/.ssh/id_rsa
RUN chmod 600 /root/.ssh/id_rsa

# Устанавливаем необходимые зависимости
RUN apt-get update && \
    apt-get install -y wget curl git openssh-feign && \
    rm -rf /var/lib/apt/lists/*
ARG JAR_FILE="config-service/build/libs/config-service.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "app.jar"]