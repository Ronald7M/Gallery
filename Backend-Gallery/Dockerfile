# Etapa 1: Construirea aplicației
FROM maven:3.8.5-openjdk-17 AS build

# Setează directorul de lucru
WORKDIR /app

# Copiază fișierul POM și codul sursă
COPY pom.xml .
COPY src ./src

# Construiește aplicația
RUN mvn clean package -DskipTests

# Etapa 2: Crearea imaginii de execuție
FROM openjdk:17-jdk-slim

# Setează directorul de lucru
WORKDIR /app

# Copiază fișierul JAR construit în etapa anterioară
COPY --from=build /app/target/*.jar app.jar

# Expune portul pe care aplicația Spring Boot va asculta
EXPOSE 8080

# Comanda pentru a porni aplicația Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]