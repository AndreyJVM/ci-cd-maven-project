# Используем надежный образ
FROM eclipse-temurin:17-jdk-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем jar-файл
COPY target/*.jar app.jar

# Открываем порт 8080
EXPOSE 8080

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]