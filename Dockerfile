FROM openjdk:17

COPY message-service/target/*.jar app.jar

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]