FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY . .

RUN ./mvnw clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENV MQTT_BROKER_URL=tcp://localhost:1883
ENV MQTT_TOPIC=sensors/temperature

ENTRYPOINT ["java", "-jar", "app.jar"]