FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY target/*.jar app.jar

COPY src/main/resources/application*.properties /app/config/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
