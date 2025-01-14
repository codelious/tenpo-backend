# Use a image based on Java JDK 17
FROM openjdk:17-jdk-slim

# Set work directory
WORKDIR /app

# Copy the JAR to the container
COPY build/libs/tenpo-backend-0.0.1-SNAPSHOT.jar app.jar

# Expose the port
EXPOSE 8080

# Execute the app
ENTRYPOINT ["java", "-jar", "app.jar"]