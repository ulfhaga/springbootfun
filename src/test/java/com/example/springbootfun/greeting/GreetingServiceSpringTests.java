package com.example.springbootfun.greeting;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(GreetingServiceSpringTests.TestConfig.class)
class GreetingServiceSpringTests {

	@Autowired
	private GreetingService greetingService;

	@Autowired
	private GreetingMessageRepository repository;

	@Test
	void createGreetingSavesMessageAndReturnsGreeting() {
		String name = "Spring";
		String expectedMessage = "Hello, Spring!";
		GreetingMessage saved = new GreetingMessage(expectedMessage, Instant.now());
		setId(saved, 42L);

		when(repository.save(any(GreetingMessage.class))).thenReturn(saved);

		Greeting greeting = greetingService.createGreeting(name);

		assertThat(greeting.id()).isEqualTo(42L);
		assertThat(greeting.message()).isEqualTo(expectedMessage);

		verify(repository).save(any(GreetingMessage.class));
	}

	private static void setId(GreetingMessage message, long id) {
		try {
			java.lang.reflect.Field field = GreetingMessage.class.getDeclaredField("id");
			field.setAccessible(true);
			field.set(message, id);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Configuration
	static class TestConfig {

		@Bean
		public GreetingMessageRepository greetingMessageRepository() {
			return mock(GreetingMessageRepository.class);
		}

		@Bean
		public GreetingService greetingService(GreetingMessageRepository repository) {
			return new GreetingService(repository);
		}

	}

}
