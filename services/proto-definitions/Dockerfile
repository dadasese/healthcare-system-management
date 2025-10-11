FROM maven:3.9.9-eclipse-temurin-21 AS proto-definitions
WORKDIR /build
COPY proto-definitions/ ./proto-definitions
RUN mvn -f proto-definitions/pom.xml clean install