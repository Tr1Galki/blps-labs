# Используем базовый образ OpenJDK
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию в контейнере
WORKDIR /app

# Копируем скомпилированный файл класса в контейнер
COPY target/lab-BEST_BLPS_LAB_BY_Tr1-Galki_AND_Satret.jar /app/target/lab-BEST_BLPS_LAB_BY_Tr1-Galki_AND_Satret.jar

# Запускаем Java-программу
ENTRYPOINT ["java", "-jar", "target/lab-BEST_BLPS_LAB_BY_Tr1-Galki_AND_Satret.jar"]