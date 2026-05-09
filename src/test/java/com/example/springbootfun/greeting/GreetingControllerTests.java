package com.example.springbootfun.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
class GreetingControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void greetingUsesDefaultName() throws Exception {
		this.mockMvc.perform(get("/api/greetings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, World!"));
	}

	@Test
	void greetingUsesRequestParameterName() throws Exception {
		this.mockMvc.perform(get("/api/greetings").param("name", "Spring"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, Spring!"));
	}

	@Test
	void greetingUsesPathName() throws Exception {
		this.mockMvc.perform(get("/api/greetings/Cursor"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", greaterThan(0)))
				.andExpect(jsonPath("$.message").value("Hello, Cursor!"));
	}

}
