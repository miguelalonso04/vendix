# ========================
# 🏗️ Etapa de construcción
# ========================
FROM eclipse-temurin:17-jdk AS build

WORKDIR /app

# Copiar solo lo necesario primero (mejor caching)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Descargar dependencias (caching eficiente)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Copiar el resto del código
COPY src src

# Construir el JAR sin ejecutar tests
RUN ./mvnw clean package -DskipTests

# =====================
# 🚀 Etapa de ejecución
# =====================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copiar solo el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["java", "-jar", "app.jar"]
