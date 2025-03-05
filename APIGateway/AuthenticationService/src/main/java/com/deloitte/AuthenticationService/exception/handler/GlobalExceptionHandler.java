package com.deloitte.AuthenticationService.exception.handler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.deloitte.AuthenticationService.exception.AuthorizationMissingException;
import com.deloitte.AuthenticationService.exception.DuplicateEntryException;
import com.deloitte.AuthenticationService.exception.InvalidCredentialsException;
import com.deloitte.AuthenticationService.exception.InvalidTokenException;
import com.deloitte.AuthenticationService.exception.UserNotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleUserNotFoundException(Exception ex) {

		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(DuplicateEntryException.class)
	public ResponseEntity<Object> handleOtherExceptions(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.CONFLICT, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Object> handleInvalidTokenExceptions(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<Object> handleInvalidCredentialsExceptions(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(AuthorizationMissingException.class)
	public ResponseEntity<Object> handleAuthorizationMissingException(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(),
				ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR,
				ex.getMessage(), ex.getLocalizedMessage(), LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleConstraintViolation(MethodArgumentNotValidException ex) {
		String errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.joining(","));

		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.BAD_REQUEST,
				"Field Validation Error Occured", errors, LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<Object>(custuomException, new HttpHeaders(), custuomException.getStatus());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
		CustomExceptionResponse custuomException = new CustomExceptionResponse(HttpStatus.BAD_REQUEST,
				"Field Validation Error Occured", "No Request Body Found", LocalDateTime.now());
		logger.error("Error Occured: {}", custuomException.toString());
		return new ResponseEntity<>(custuomException, HttpStatus.BAD_REQUEST);
	}
}
