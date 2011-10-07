package com.gwtplatform.samples.nested.shared.exception;

public class LogoutException extends Exception {

	private static final long serialVersionUID = 7043694250123042731L;

	public LogoutException() {
	}

	public LogoutException(String message) {
		super(message);
	}

	public LogoutException(String message, Throwable cause) {
		super(message + " (" + cause.getMessage() + ")");
	}

	public LogoutException(Throwable cause) {
		super(cause.getMessage());
	}
}
