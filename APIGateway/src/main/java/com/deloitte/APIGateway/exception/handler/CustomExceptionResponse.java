package com.deloitte.APIGateway.exception.handler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CustomExceptionResponse {

	private HttpStatus status;
	private String message;
	private List<String> errors;

	private LocalDateTime date;

	public CustomExceptionResponse(HttpStatus status, String message, List<String> errors, LocalDateTime date) {
		super();
		this.status = status;
		this.message = message;
		this.date = date;
		this.errors = errors;
	}

	public CustomExceptionResponse(HttpStatus status, String message, String error, LocalDateTime date) {
		super();
		this.status = status;
		this.message = message;
		this.date = date;
		errors = Arrays.asList(error);
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}