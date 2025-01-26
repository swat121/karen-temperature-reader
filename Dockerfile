FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:17

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV MQTT_BROKER_URL=tcp://localhost:1883
ENV MQTT_TOPIC=sensors/temperature

ENTRYPOINT ["java", "-jar", "app.jar"]