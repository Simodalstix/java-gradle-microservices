FROM eclipse-temurin:17-jre
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8201
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=prod"]