package com.example.springbootfun.bdd;

import java.util.List;

import com.jayway.jsonpath.JsonPath;
import com.example.springbootfun.greeting.GreetingMessage;
import com.example.springbootfun.greeting.GreetingMessageRepository;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class GreetingStepDefinitions {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GreetingMessageRepository repository;

	private MvcResult response;

	private GreetingMessage storedGreeting;

	@Given("there are no stored greeting messages")
	public void thereAreNoStoredGreetingMessages() {
		this.repository.deleteAll();
		this.response = null;
		this.storedGreeting = null;
	}

	@When("I request the default greeting")
	public void iRequestTheDefaultGreeting() throws Exception {
		this.response = this.mockMvc.perform(get("/api/greetings")).andReturn();
	}

	@When("I request a greeting for {string} using the query parameter")
	public void iRequestAGreetingForUsingTheQueryParameter(String name) throws Exception {
		this.response = this.mockMvc.perform(get("/api/greetings").param("name", name)).andReturn();
	}

	@When("I request a greeting for {string} using the path variable")
	public void iRequestAGreetingForUsingThePathVariable(String name) throws Exception {
		this.response = this.mockMvc.perform(get("/api/greetings/{name}", name)).andReturn();
	}

	@Then("the greeting response message should be {string}")
	public void theGreetingResponseMessageShouldBe(String expectedMessage) throws Exception {
		assertThat(this.response.getResponse().getStatus()).isEqualTo(200);

		String responseBody = this.response.getResponse().getContentAsString();
		Number id = JsonPath.read(responseBody, "$.id");
		String message = JsonPath.read(responseBody, "$.message");
		assertThat(id.longValue()).isPositive();
		assertThat(message).isEqualTo(expectedMessage);
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
