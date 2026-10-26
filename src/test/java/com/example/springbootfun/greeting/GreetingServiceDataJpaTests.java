package com.example.springbootfun.greeting;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Enhetstest med Spring Boot:s testskiva {@link DataJpaTest}.
 * <p>
 * {@code @DataJpaTest} laddar endast JPA-relaterade komponenter (entiteter,
 * Spring Data-repositories, EntityManager, transaktionshantering) och konfigurerar
 * automatiskt en inbäddad databas. Webkomponenter som kontroller, filter och
 * servletcontainer startas inte, vilket gör testet snabbare än
 * {@code @SpringBootTest}.
 * </p>
 * <p>
 * Testet inkluderar inte serviceklasser sommåste {@link GreetingService}
 * importeras explicit med {@code @Import}. Varje test körs i en egen transaktion
 * som automatiskt rullas tillbaka, så inget tillstånd läcker mellan tester.
 * </p>
 */
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

		Greeting greeting = greetingService.createGreeting("Ulf");

		assertThat(greeting.id()).isGreaterThan(0);
		assertThat(greeting.message()).isEqualTo("Hello, Ulf!");

		List<GreetingMessage> messages = repository.findAll();
		assertThat(messages).hasSize(1);
		assertThat(messages.get(0).getMessage()).isEqualTo("Hello, Ulf!");
		assertThat(messages.get(0).getCreatedAt()).isNotNull();
	}

}
