# Use an OpenJDK base image
FROM openjdk:17-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy jar file (replace with your actual JAR name)
COPY target/employee-app.jar employee-app.jar

# Expose port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "employee-app.jar"]
