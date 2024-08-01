FROM maven:3.9.7-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
COPY src/main/resources/static/file.csv src/main/resources/static/
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17
WORKDIR /app
EXPOSE 8080
COPY --from=build /app/target/*.jar /app/carservice.jar
COPY --from=build /app/src/main/resources/static/file.csv src/main/resources/static/file.csv
ENTRYPOINT ["java", "-jar", "carservice.jar"]
