FROM openjdk:17.0

ARG APP_NAME=weather-api-main
ARG TARGET_JAR_FILE=main/target/${APP_NAME}.jar

WORKDIR /opt/${APP_NAME}

COPY ${TARGET_JAR_FILE} ${APP_NAME}.jar

ENTRYPOINT ["java", "-jar", "weather-api-main.jar", "--spring.profiles.active=docker"]

EXPOSE 8080