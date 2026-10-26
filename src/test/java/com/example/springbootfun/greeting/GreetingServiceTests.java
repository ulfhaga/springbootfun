package com.example.springbootfun.greeting;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GreetingServiceTests {

	@Mock
	private GreetingMessageRepository repository;

	@InjectMocks
	private GreetingService greetingService;

	@Test
	void createGreetingSavesMessageAndReturnsGreeting() {
		String name = "Test";
		String expectedMessage = "Hello, Test!";
		GreetingMessage saved = new GreetingMessage(expectedMessage, Instant.now());
		setId(saved, 1L);

		when(repository.save(any(GreetingMessage.class))).thenReturn(saved);

		Greeting greeting = greetingService.createGreeting(name);

		assertThat(greeting.id()).isEqualTo(1L);
		assertThat(greeting.message()).isEqualTo(expectedMessage);

		ArgumentCaptor<GreetingMessage> captor = ArgumentCaptor.forClass(GreetingMessage.class);
		verify(repository).save(captor.capture());
		assertThat(captor.getValue().getMessage()).isEqualTo(expectedMessage);
		assertThat(captor.getValue().getCreatedAt()).isNotNull();
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

}
