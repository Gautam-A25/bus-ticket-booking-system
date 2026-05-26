# ----- Build stage -----
# Use Eclipse Temurin JDK 17 on Alpine Linux as the base image (lightweight, production-grade)
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the Maven wrapper and project descriptor first so Docker can cache
# the dependency-download layer and only re-run it when pom.xml changes
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

# Make the Maven wrapper script executable (required on Linux)
RUN chmod +x mvnw

# Build the fat JAR, skipping tests to keep image builds fast
# The resulting JAR is placed in target/
RUN ./mvnw clean package -DskipTests

# Expose port 8080 so Docker and orchestrators know which port to forward
EXPOSE 8080

# Health check: poll the root endpoint every 30 seconds after a 40-second start-up grace period
# The container is marked "unhealthy" after 3 consecutive failures
HEALTHCHECK --interval=30s --timeout=10s --start-period=40s --retries=3 \
  CMD wget -q -O /dev/null http://localhost:8080/ || exit 1

# Start the Spring Boot application using the packaged JAR
CMD ["java", "-jar", "target/BusTicketBooking-0.0.1-SNAPSHOT.jar"]
