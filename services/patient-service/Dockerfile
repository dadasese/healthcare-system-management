FROM proto-definitions:latest AS proto-definitions
WORKDIR /workspace
COPY /pom.xml ./pom.xml
RUN mvn dependency:go-offline -B || true
COPY /src ./src
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app
COPY --from=proto-definitions /workspace/target/*.jar ./app.jar
EXPOSE 4000
ENTRYPOINT ["java", "-jar", "app.jar"]