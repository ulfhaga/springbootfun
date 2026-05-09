package com.example.springbootfun.greeting;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

	private final AtomicLong counter = new AtomicLong();

	@GetMapping
	public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
		return buildGreeting(name);
	}

	@GetMapping("/{name}")
	public Greeting greetingByName(@PathVariable String name) {
		return buildGreeting(name);
	}

	private Greeting buildGreeting(String name) {
		return new Greeting(counter.incrementAndGet(), "Hello, %s!".formatted(name));
	}

}
