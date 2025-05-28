# ========================
# 🏗️ Etapa de construcción
# ========================
FROM eclipse-temurin:23-jdk AS build

WORKDIR /app

# Copiar solo lo necesario primero (mejor caching)
COPY . .

# Descargar dependencias (caching eficiente)
RUN chmod +x mvnw && ./mvnw dependency:go-offline -B

# Construir el JAR sin ejecutar tests
RUN ./mvnw clean package -DskipTests

# =====================
# 🚀 Etapa de ejecución
# =====================
FROM eclipse-temurin:23-jdk

WORKDIR /app

# Copiar solo el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto de la app
EXPOSE 8080

# Comando para iniciar la aplicación
CMD ["java", "-jar", "app.jar"]
