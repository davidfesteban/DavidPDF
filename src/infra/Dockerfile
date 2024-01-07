# Use a base image with Java (e.g., OpenJDK) installed
FROM openjdk:22-slim-bullseye

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY target/html2pdf-0.0.1-SNAPSHOT.jar /app/html2pdf.jar

# Define the command to run your Spring Boot application
CMD ["java", "-jar", "html2pdf.jar"]