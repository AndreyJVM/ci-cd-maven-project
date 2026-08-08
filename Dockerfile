# Используем легкий образ с Java
FROM openjdk:17-ea-17-jdk-slim

# Указываем рабочую директорию внутри контейнера
WORKDIR /app

# Копируем собранный jar-файл из target в контейнер
# (название файла зависит от artifactId и version в pom.xml)
COPY target/*.jar app.jar

# Открываем порт (если ваше приложение слушает порт, например 8080)
#EXPOSE 8080

# Команда запуска
ENTRYPOINT ["java", "-jar", "app.jar"]