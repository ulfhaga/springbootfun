package com.example.springbootfun.greeting;

import java.util.List;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GreetingRestAssuredIntegrationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private GreetingMessageRepository repository;

	@BeforeEach
	void setUp() {
		RestAssured.port = this.port;
		this.repository.deleteAll();
	}

	@Test
	void greetingEndpointStoresMessageInH2Database() {
		RestAssured.given()
				.queryParam("name", "Integration")
				.when()
				.get("/api/greetings")
				.then()
				.statusCode(200)
				.body("id", greaterThan(0))
				.body("message", equalTo("Hello, Integration!"));

		assertStoredMessages("Hello, Integration!");
	}

	@Test
	void pathGreetingEndpointStoresMessageInH2Database() {
		RestAssured.given()
				.pathParam("name", "RestAssured")
				.when()
				.get("/api/greetings/{name}")
				.then()
				.statusCode(200)
				.body("id", greaterThan(0))
				.body("message", equalTo("Hello, RestAssured!"));

		assertStoredMessages("Hello, RestAssured!");
	}

	private void assertStoredMessages(String expectedMessage) {
		List<GreetingMessage> messages = this.repository.findAll();
		assertThat(messages).singleElement()
				.satisfies(message -> {
					assertThat(message.getMessage()).isEqualTo(expectedMessage);
					assertThat(message.getCreatedAt()).isNotNull();
				});
	}

}
