# Stage 1: Build the Angular app
FROM node:18-alpine AS build-angular
WORKDIR /app
COPY ./web/ ./

RUN rm -rf ./angular
RUN rm -rf ./node_modules
RUN rm -rf ./dist

RUN npm install
RUN npm run build --prod

# Stage 2: Build the Spring Boot app and copy the Angular built files
FROM maven:3.8.5-openjdk-17 AS build-spring
WORKDIR /app

COPY api ./api
COPY logic ./logic
COPY pom.xml ./

RUN mvn clean package -DskipTests

# Stage 3: Prepare the final image with Tomcat
FROM openjdk:17-oracle

# Set the working directory
RUN mkdir -p /app
WORKDIR /app

# Copy the Angular build files to the ROOT directory
COPY --from=build-angular /app/dist/web /app/resources/public

# Copy the Spring Boot war file to the webapps directory
COPY --from=build-spring /app/api/target/api.war /app/api.war

# Make port 8080 available to the world outside this container
EXPOSE 8080

# Run Tomcat
# CMD ["catalina.sh", "run"]
CMD ["java", "-jar", "/app/api.war", "--spring.web.resources.static-locations=file:/app/resources/public"]
