FROM openjdk:17.0

ARG APP_NAME=weather-api
ARG TARGET_JAR_FILE=target/${APP_NAME}.jar

WORKDIR /opt/${APP_NAME}

COPY ${TARGET_JAR_FILE} ${APP_NAME}.jar

ENTRYPOINT ["java", "-jar", "weather-api.jar", "--spring.profiles.active=docker"]

EXPOSE 8080