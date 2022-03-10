package com.thubas.sotiras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class VerificationTokenException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public VerificationTokenException(String message) {
		super(message);
	}

}
