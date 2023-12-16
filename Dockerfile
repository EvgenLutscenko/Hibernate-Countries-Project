FROM openjdk:17-jdk-slim
WORKDIR /app

COPY target/*.jar app.jar
COPY target/lib /app/lib

ENTRYPOINT ["java", "-cp", "app.jar:lib/*", "com.javarush.ua.lutscenko.Main"]
EXPOSE 8080