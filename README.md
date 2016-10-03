# Full-stack application

- AngularJS 2 frontend
- Spring Boot backend
- File based HSQLDB

## Prerequisites

- `mvn` 3.x.x or higher
- `npm` 3.x.x or higher

## Database

To create and populate file based HSQLDB

    cp src/test/resources/*.sql src/main/resources/
    mkdir db
    mvn clean spring-boot:run
    rm src/main/resources/*.sql

## Development

    mvn clean test
    mvn spring-boot:run

## Deployment

    mvn clean package
    java -jar target/pet-store-1.0-SNAPSHOT.jar

## Testing API

    curl -v -X POST -d '{"name":"Roger"}' -H "content-type:application/json" http://localhost:8080/api/pet
    curl -v -X GET  http://localhost:8080/api/pet/21
    curl -v -X PUT -d '{"id":21,"name":"Roger 2"}' -H "content-type:application/json" http://localhost:8080/api/pet
    curl -v -X DELETE  http://localhost:8080/api/pet/21


