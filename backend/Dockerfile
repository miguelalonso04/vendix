# Etapa de construcción
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copia el wrapper mvnw y el directorio .mvn primero para aprovechar la cache de Docker
COPY mvnw .
COPY .mvn .mvn

# Dale permisos de ejecución al mvnw
RUN chmod +x mvnw

# Copia el resto del proyecto
COPY pom.xml .
COPY src src

# Construye el .jar usando Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Etapa de ejecución
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copia el .jar desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto (Railway lo sobrescribe con $PORT)
EXPOSE 8080

# Ejecuta la aplicación
CMD ["java", "-jar", "app.jar"]
