package com.example.springbootfun.greeting;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GreetingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private GreetingMessageRepository repository;

	@BeforeEach
	void deleteGreetingMessages() {
		this.repository.deleteAll();
	}

	@Test
	void greetingUsesDefaultName() throws Exception {
		this.mockMvc.perform(get("/api/greetings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, World!"));

		assertStoredMessages("Hello, World!");
	}

	@Test
	void greetingUsesRequestParameterName() throws Exception {
		this.mockMvc.perform(get("/api/greetings").param("name", "Spring"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, Spring!"));

		assertStoredMessages("Hello, Spring!");
	}

	@Test
	void greetingUsesPathName() throws Exception {
		this.mockMvc.perform(get("/api/greetings/Cursor"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, Cursor!"));

		assertStoredMessages("Hello, Cursor!");
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
