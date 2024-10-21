# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /moder-app

# Копируем скомпилированный файл класса в контейнер
COPY moder_service/target/moder_service-moder-SNAPSHOT.jar /moder-app/target/moder-SNAPSHOT.jar

# Запускаем Java-программу
ENTRYPOINT ["java", "-jar", "/moder-app/target/moder-SNAPSHOT.jar"]