########## Build Stage ##########
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy only pom first (cache deps)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the app
RUN mvn clean package -DskipTests

########## Runtime Stage ##########
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Install timezone & certificates (important)
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Africa/Nairobi /etc/localtime && \
    echo "Africa/Nairobi" > /etc/timezone && \
    apk add --no-cache ca-certificates

# Copy Jar
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 9091

ENTRYPOINT ["java", "-jar", "app.jar"]

