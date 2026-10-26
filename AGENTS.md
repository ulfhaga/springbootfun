# AGENTS.md

## Commands

- Run app: `./mvnw spring-boot:run` → http://localhost:8080
- Run all tests: `./mvnw test` (currently 9 tests across 4 classes)
- Single test class: `./mvnw -Dtest=GreetingControllerTests test`
- Cucumber BDD only: `./mvnw -Dtest=CucumberTest test`
- Use `./mvnw` (Maven 3.9.15); a system `mvn` 3.8.7 also exists but is not the project version.
- Build targets Java 21 (`java.version` in `pom.xml`); the installed JDK 25 compiles and runs it fine.
- No lint/format/typecheck config and no CI, so `./mvnw test` is the whole verification step.
- If Maven times out against `nexus.arbetsformedlingen.se` (the global `~/.m2/settings.xml` mirrors all repositories to an internal Nexus), pass `-s <settings.xml>` with a Maven Central mirror.

## Architecture

- Spring Boot 4.0.6, Maven single module (`com.example:springbootfun`). Use `spring-boot-starter-webmvc` / `spring-boot-starter-webmvc-test`; Boot 4 relocated MockMvc's annotation to `org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc` (the Boot 3 `org.springframework.boot.test.autoconfigure.web.servlet.*` import does not resolve).
- All domain code is in `com.example.springbootfun.greeting`: `GreetingController` (`/api/greetings`, `?name=` and `/{name}`) → `GreetingService.createGreeting()` → `GreetingMessageRepository` → `GreetingMessage` (`greeting_messages` table). Responses are `Greeting(long id, String message)`, formatted `Hello, <name>!`.
- There is no read-only endpoint: every GET persists a row with `message` + `createdAt` (`@Transactional` in `GreetingService`).
- Persistence: embedded H2 in-memory (`jdbc:h2:mem:greetingsdb`, user `sa`, empty password), schema via `spring.jpa.hibernate.ddl-auto=update` — no migrations. H2 console at `/h2-console`.
- Config is only `src/main/resources/application.properties`; no profiles, env files, or external services.

## Tests

- Three layers cover the same greeting behavior — update all of them plus the feature file when changing endpoints:
  - `GreetingControllerTests`: MockMvc, `@SpringBootTest` + `@AutoConfigureMockMvc` (no port).
  - `GreetingRestAssuredIntegrationTests`: real HTTP on `RANDOM_PORT`, sets `RestAssured.port` in `@BeforeEach`.
  - Cucumber: features in `src/test/resources/features/`, glue in `bdd/` package, runner `CucumberTest` uses `@SelectPackages("features")` + `cucumber-spring` with `RANDOM_PORT` (`CucumberSpringConfiguration`); steps read `${local.server.port}`.
- All test classes share the same in-memory database within a JVM run, and tests assert exactly one stored row. Call `repository.deleteAll()` before each request (existing tests do this in `@BeforeEach` or a Given step); do not assume test isolation.
- Cucumber discovery quirk: `@SelectPackages("features")` refers to the classpath package backed by `src/test/resources/features` (not a Java package), and glue is fixed to `com.example.springbootfun.bdd`. New features/step definitions outside those locations won't run.
