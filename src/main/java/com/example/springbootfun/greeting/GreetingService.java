package com.example.springbootfun.greeting;

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
		GreetingMessage savedMessage = this.repository.save(new GreetingMessage(message));
		return new Greeting(savedMessage.getId(), savedMessage.getMessage());
	}

}
