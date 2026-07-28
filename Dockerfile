# --- Application Build ---
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn package dependency:copy-dependencies -DskipTests

# --- Runtime Image ---
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/target/dependency /app/lib
COPY --from=build /app/target/classes /app/classes

EXPOSE 8080

ENTRYPOINT ["java", "-cp", "/app/classes:/app/lib/*", "org.mifra.Main"]