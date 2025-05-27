# Etapa de construcción
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copia el proyecto completo
COPY . .

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
