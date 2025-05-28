# Etapa de construcción
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copiar solo el backend
COPY backend backend

WORKDIR /app/backend

RUN chmod +x mvnw && ./mvnw dependency:go-offline -B
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:23-jdk

WORKDIR /app

COPY --from=build /app/backend/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

