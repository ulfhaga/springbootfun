package com.example.springbootfun.greeting;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

	private final GreetingService greetingService;

	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}

	@GetMapping
	public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
		return this.greetingService.createGreeting(name);
	}

	@GetMapping("/{name}")
	public Greeting greetingByName(@PathVariable String name) {
		return this.greetingService.createGreeting(name);
	}

}
