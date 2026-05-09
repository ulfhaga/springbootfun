# springbootfun

A minimal Spring Boot 4 RESTful API example built with Maven. Each generated greeting message is stored in an embedded H2 database.

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

Every request stores the returned greeting message in the `greeting_messages` table with a `created_at` timestamp. The H2 console is enabled at:

```text
http://localhost:8080/h2-console
```

Use `jdbc:h2:mem:greetingsdb` as the JDBC URL, `sa` as the username, and leave the password empty.

## Test

```bash
./mvnw test
```

The test suite includes Cucumber behavior-driven scenarios in `src/test/resources/features`. It also includes RestAssured integration tests that call the running Spring Boot API and verify the stored H2 records.
