package com.deloitte.OrderService.exception.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deloitte.OrderService.exception.DuplicateEntryException;
import com.deloitte.OrderService.exception.MissingAuthorizationTokenException;
import com.deloitte.OrderService.exception.OrderCreationException;
import com.deloitte.OrderService.exception.OrderException;
import com.deloitte.OrderService.exception.OrderNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleConstraintViolation(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(","));

		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.BAD_REQUEST,
				"Field Validation Error Occured", errors, LocalDateTime.now());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception ex) {

		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(OrderException.class)
	public ResponseEntity<Object> handleOrderExceptions(Exception ex) {
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

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.BAD_REQUEST,
				"Field Validation Error Occured", "No Request Body Found", LocalDateTime.now());
		return new ResponseEntity<>(custuomException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(OrderCreationException.class)
	protected ResponseEntity<Object> handleOrderCreationException(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.BAD_REQUEST,
				ex.getMessage(), ex.getLocalizedMessage(), LocalDateTime.now());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}
	
	@ExceptionHandler({MissingAuthorizationTokenException.class })
	protected ResponseEntity<Object> handleResturantException(HttpMessageNotReadableException ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.FORBIDDEN, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		return new ResponseEntity<>(custuomException, HttpStatus.FORBIDDEN);
	}
}
