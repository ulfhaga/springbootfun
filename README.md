# springbootfun

A minimal Spring Boot 4 RESTful API example built with Maven.

## Requirements

- Java 21
- Maven is provided by the included Maven Wrapper (`./mvnw`)

## Run the application

```bash
./mvnw spring-boot:run
```

The application starts on `http://localhost:8080`.

## REST endpoints

Return a default greeting:

```bash
curl http://localhost:8080/api/greetings
```

```json
{
  "id": 1,
  "message": "Hello, World!"
}
```

Return a greeting using a query parameter:

```bash
curl "http://localhost:8080/api/greetings?name=Spring"
```

Return a greeting using a path variable:

```bash
curl http://localhost:8080/api/greetings/Cursor
```

## Test

```bash
./mvnw test
```
