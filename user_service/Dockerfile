# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /user-app

# Копируем скомпилированный файл класса в контейнер
COPY user_service/target/user_service-user-SNAPSHOT.jar /user-app/target/user-SNAPSHOT.jar

# Запускаем Java-программу
ENTRYPOINT ["java", "-jar", "/user-app/target/user-SNAPSHOT.jar"]