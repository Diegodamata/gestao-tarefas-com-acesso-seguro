# Build
FROM maven:3.8.8-amazoncorretto-8-al2023 as build
WORKDIR /build

COPY . .

RUN mvn clean package -DskipTests

# Run
FROM amazoncorretto:21.0.5
WORKDIR /app

COPY --from=build ./build/target/*.jar ./taskmanager.jar

EXPOSE 8080
EXPOSE 9090

ENV DATASOURCE_URL=''
ENV DATASOURCE_USERNAME=''
ENV DATASOURCE_PASSWORD=''
ENV GOOGLE_CLIENTE_ID=''
ENV GOOGLE_CLIENT_SECRET=''

ENV SPRING_PROFILES_ACTIVE='dev'
ENV TZ='America/Sao_Paulo'

ENTRYPOINT java -jar taskmanager.jar