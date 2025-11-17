FROM eclipse-temurin:21-jdk

# Set the working directory in the container
WORKDIR /projeto

# Copy the JAR file into the container at /educacaoGamificada
COPY target/*.jar /projeto/projeto-0.0.1-SNAPSHOT.jar

# Expose the port that your application will run on
EXPOSE 8081

# Specify the command to run on container start
CMD ["java", "-jar", "projeto-0.0.1-SNAPSHOT.jar"]