package com.deloitte.APIGateway.exception.handler;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deloitte.APIGateway.exception.MissingAuthorizationTokenException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MissingAuthorizationTokenException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception ex) {

		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getMessage(), ex.getLocalizedMessage(), LocalDateTime.now());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}
}
