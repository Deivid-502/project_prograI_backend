# Etapa 1: Build con Maven
FROM maven:3.9.4-eclipse-temurin-20 AS build
WORKDIR /app

# Copiamos pom y fuentes
COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen de ejecución
FROM eclipse-temurin:20-jdk-alpine
WORKDIR /app

# Copiamos el JAR compilado
COPY --from=build /app/target/*.jar app.jar

# Puerto en el que el Spring Boot escucha
EXPOSE 8080

# Comando de arranque
ENTRYPOINT ["java", "-jar", "app.jar"]
