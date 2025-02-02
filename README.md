# Komgo Library-Service test

A library-service to manage library : 

* Add a new book to the catalog 
* A user can borrow a book 
* A user can return a book 
* Print a list of the books that I borrowed

## Architecture

Based on the _Hexagonal architecture_, using Java-springboot and gradle.

## Rest API Documentation

Based on the `Contract First` approach, the Open-Api specification of this project is available [here](contract/library-service.openapi.yaml).

## Configuration

No configuration required for this simple service.

Example `application.yml`:

```
N.A
```

## How to build and run in Docker

1) build : `./gradlew assemble`
2) start the library-service using docker-compose : `docker compose up --build`

## How to run tests

4 test levels were implemented :
- **Unit Tests**
- **Integration Tests**
- **System Tests** : using `Karate` and `test-containers` to automatically build, run and test the application inside the docker environment, the Karate reports will be generated in `build/reports/karate`.
- **Load / Performance Tests** : using Gatling (the application should already been started), the reports will be generated in `build/reports/gatling`.

### Run Unit Tests

`./gradlew test`

### Run Integration Tests

`./gradlew integrationTest`

### Run System Tests

`./gradlew assemble systemTest`

### Run Perf Tests

`./gradlew gatlingRun -D karate.env=local`