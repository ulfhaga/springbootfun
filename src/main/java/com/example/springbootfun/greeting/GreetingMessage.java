package com.example.springbootfun.greeting;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "GREETING_MESSAGES")
public class GreetingMessage {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "greeting_message_seq_gen")
	@SequenceGenerator(name = "greeting_message_seq_gen", sequenceName = "GREETING_MESSAGE_SEQ", allocationSize = 1)
	private Long id;

	@Column(nullable = false, length = 255)
	private String message;

	@Column(name = "CREATED_AT", nullable = false)
	private Instant createdAt;

	protected GreetingMessage() {
	}

	public GreetingMessage(String message, Instant createdAt) {
		this.message = message;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return this.id;
	}

	public String getMessage() {
		return this.message;
	}

	public Instant getCreatedAt() {
		return this.createdAt;
	}

}
