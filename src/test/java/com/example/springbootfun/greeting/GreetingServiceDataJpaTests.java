package com.example.springbootfun.greeting;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(GreetingService.class)
class GreetingServiceDataJpaTests {

	@Autowired
	private GreetingService greetingService;

	@Autowired
	private GreetingMessageRepository repository;

	@Test
	void createGreetingSavesMessageAndReturnsGreeting() {
		repository.deleteAll();

		Greeting greeting = greetingService.createGreeting("DataJpa");

		assertThat(greeting.id()).isGreaterThan(0);
		assertThat(greeting.message()).isEqualTo("Hello, DataJpa!");

		List<GreetingMessage> messages = repository.findAll();
		assertThat(messages).hasSize(1);
		assertThat(messages.get(0).getMessage()).isEqualTo("Hello, DataJpa!");
		assertThat(messages.get(0).getCreatedAt()).isNotNull();
	}

}
