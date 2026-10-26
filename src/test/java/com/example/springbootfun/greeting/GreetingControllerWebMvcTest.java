package com.example.springbootfun.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GreetingController.class)
class GreetingControllerWebMvcTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private GreetingService greetingService;

	@Test
	void greetingUsesDefaultName() throws Exception {
		when(this.greetingService.createGreeting("World"))
				.thenReturn(new Greeting(1L, "Hello, World!"));

		this.mockMvc.perform(get("/api/greetings"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.message").value("Hello, World!"));
	}

	@Test
	void greetingUsesRequestParameterName() throws Exception {
		when(this.greetingService.createGreeting("Spring"))
				.thenReturn(new Greeting(2L, "Hello, Spring!"));

		this.mockMvc.perform(get("/api/greetings").param("name", "Spring"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(2L))
				.andExpect(jsonPath("$.message").value("Hello, Spring!"));
	}

	@Test
	void greetingUsesPathName() throws Exception {
		when(this.greetingService.createGreeting("Cursor"))
				.thenReturn(new Greeting(3L, "Hello, Cursor!"));

		this.mockMvc.perform(get("/api/greetings/Cursor"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(3L))
				.andExpect(jsonPath("$.message").value("Hello, Cursor!"));
	}

}
