# Use a Java development kit as the base image
FROM amazoncorretto:17-alpine-jdk as build

# Set the working directory to /app
WORKDIR /app

# Copy the source code to the container
COPY . .

# Build the JAR file
RUN ./mvnw clean package -DskipTests

# Use a Java runtime as the base image for the final image
FROM amazoncorretto:17-alpine-jdk

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build image to the final image
COPY --from=build /app/target/dts-0.0.1-SNAPSHOT.jar /app/dts-web.jar

# Expose the port that the app listens on
EXPOSE 8080

# Set the command to run the app when the container starts
CMD ["java", "-jar", "/app/dts-web.jar"]

