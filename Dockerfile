# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy as base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base as development
CMD ["./mvnw", "spring-boot:run"]

# for debugging
# CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base as build
RUN ./mvnw package -Dmaven.test.skip

FROM eclipse-temurin:17-jre-jammy as production
EXPOSE 8080
COPY --from=build /app/target/dts-*.jar /dts.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/dts.jar"]

# FROM eclipse-temurin:17-jdk-jammy as build

# #### Stage 1: Build the application
# # FROM adoptopenjdk/openjdk11:ubi as build

# # Set the current working directory inside the image
# WORKDIR /app

# # Copy maven executable to the image
# COPY .mvn/ .mvn
# COPY mvnw pom.xml ./

# # Build all the dependencies in preparation to go offline. 
# # This is a separate step so the dependencies will be cached unless 
# # the pom.xml file has changed.
# RUN ./mvnw dependency:resolve

# # Copy the project source
# COPY src src

# # CMD ["./mvnw","spring-boot:run"]

# # # Package the application
# RUN ./mvnw package -Dmaven.test.skip
# RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)


# ##### Stage 2: A minimal docker image with command to run the app 
# FROM adoptopenjdk/openjdk11:ubi

# ARG DEPENDENCY=/app/target/dependency

# # Copy project dependencies from the build stage
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

# #CMD ["./mvnw","spring-boot:run"]

# ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.art.DtsApplication"]

