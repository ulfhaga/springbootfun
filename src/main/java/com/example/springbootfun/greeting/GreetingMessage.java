package com.example.springbootfun.greeting;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "greeting_messages")
public class GreetingMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String message;

	protected GreetingMessage() {
	}

	public GreetingMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

}
