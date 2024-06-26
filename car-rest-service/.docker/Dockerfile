FROM eclipse-temurin:17 AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src/main/resources/static/file.csv src/main/resources/static/
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install

FROM eclipse-temurin:17 AS final
WORKDIR /opt/app
EXPOSE 8080
COPY --from=builder /opt/app/target/*.jar /opt/app/carservice.jar
COPY --from=builder /opt/app/src/main/resources/static/file.csv src/main/resources/static/file.csv
ENTRYPOINT ["java", "-jar", "carservice.jar"]
