package com.deloitte.APIGateway.exception;

public class MissingAuthorizationTokenException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public MissingAuthorizationTokenException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MissingAuthorizationTokenException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MissingAuthorizationTokenException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MissingAuthorizationTokenException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MissingAuthorizationTokenException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
	
}
