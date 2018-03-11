# Full-stack application

- Angular client
- Spring Boot server
- File based HSQLDB

## Prerequisites

- `mvn` 3.x.x or higher
- `npm` 5.x.x or higher

## Database

To create and populate file based HSQLDB

    cp src/test/resources/*.sql src/main/resources/
    mkdir db
    mvn clean spring-boot:run
    rm src/main/resources/*.sql
