# Simple Spring Boot Server

## Development server

Run `mvn spring-boot:run -Dspring.profiles.active=memory` for a dev server.
Navigate to Swagger UI [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

## Running unit tests

Run `mvn clean test` to execute the JUnit tests.

## API Reference

Run `open target/generated-docs/api-reference.html` to read API Reference documentation.

## Deployment

Build the Client first. Then run

    ln -s ../client/dist src/main/resources/static
    mvn clean package
    java -jar target/pet-store-1.0-SNAPSHOT.jar

## Testing API

    curl -v -X POST -d '{"name":"Roger"}' -H "content-type:application/json" http://localhost:8080/api/pet
    curl -v -X GET  http://localhost:8080/api/pet/21
    curl -v -X PUT -d '{"id":21,"name":"Roger 2"}' -H "content-type:application/json" http://localhost:8080/api/pet
    curl -v -X DELETE  http://localhost:8080/api/pet/21
