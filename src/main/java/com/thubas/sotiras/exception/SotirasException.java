package com.thubas.sotiras.exception;

public class SotirasException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SotirasException() {
		super();
	}

	public SotirasException(String message, Throwable cause) {
		super(message, cause);
	}

	public SotirasException(String message) {
		super(message);
	}

	public SotirasException(Throwable cause) {
		super(cause);
	}
	



}
