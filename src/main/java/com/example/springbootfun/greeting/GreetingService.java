package com.example.springbootfun.greeting;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GreetingService {

	private final GreetingMessageRepository repository;

	public GreetingService(GreetingMessageRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public Greeting createGreeting(String name) {
		String message = "Hello, %s!".formatted(name);
		GreetingMessage savedMessage = this.repository.save(new GreetingMessage(message, Instant.now()));
		return new Greeting(savedMessage.getId(), savedMessage.getMessage());
	}

}
