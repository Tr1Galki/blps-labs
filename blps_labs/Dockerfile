# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем скомпилированный файл класса в контейнер
COPY target/blps-comunda.jar /app/blps-comunda.jar

# Запускаем Java-программу
ENTRYPOINT ["java", "-jar", "blps-comunda.jar"]