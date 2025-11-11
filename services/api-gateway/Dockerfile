FROM maven:3.9.9-eclipse-temurin-21 AS api-gateway
WORKDIR /workspace
COPY pom.xml .
RUN mvn dependency:go-offline -B || true
COPY /src ./src
RUN mvn clean package

FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app
COPY --from=api-gateway /workspace/target/*.jar ./app.jar
EXPOSE 4004
ENTRYPOINT ["java","-jar", "app.jar"]