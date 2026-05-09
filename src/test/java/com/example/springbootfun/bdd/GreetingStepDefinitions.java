package com.example.springbootfun.bdd;

import java.util.List;

import com.example.springbootfun.greeting.GreetingMessage;
import com.example.springbootfun.greeting.GreetingMessageRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class GreetingStepDefinitions {

	@Value("${local.server.port}")
	private int port;

	@Autowired
	private GreetingMessageRepository repository;

	private Response response;

	private GreetingMessage storedGreeting;

	@Given("there are no stored greeting messages")
	public void thereAreNoStoredGreetingMessages() {
		this.repository.deleteAll();
		this.response = null;
		this.storedGreeting = null;
	}

	@When("I request the default greeting")
	public void iRequestTheDefaultGreeting() {
		this.response = given()
				.port(this.port)
				.when()
				.get("/api/greetings");
	}

	@When("I request a greeting for {string} using the query parameter")
	public void iRequestAGreetingForUsingTheQueryParameter(String name) {
		this.response = given()
				.port(this.port)
				.queryParam("name", name)
				.when()
				.get("/api/greetings");
	}

	@When("I request a greeting for {string} using the path variable")
	public void iRequestAGreetingForUsingThePathVariable(String name) {
		this.response = given()
				.port(this.port)
				.pathParam("name", name)
				.when()
				.get("/api/greetings/{name}");
	}

	@Then("the greeting response message should be {string}")
	public void theGreetingResponseMessageShouldBe(String expectedMessage) {
		this.response.then()
				.statusCode(200)
				.body("id", greaterThan(0))
				.body("message", equalTo(expectedMessage));
	}

	@Then("the greeting should be stored with message {string}")
	public void theGreetingShouldBeStoredWithMessage(String expectedMessage) {
		List<GreetingMessage> messages = this.repository.findAll();
		assertThat(messages).singleElement()
				.satisfies(message -> {
					assertThat(message.getMessage()).isEqualTo(expectedMessage);
					this.storedGreeting = message;
				});
	}

	@Then("the stored greeting should have a creation timestamp")
	public void theStoredGreetingShouldHaveACreationTimestamp() {
		assertThat(this.storedGreeting).isNotNull();
		assertThat(this.storedGreeting.getCreatedAt()).isNotNull();
	}

}
