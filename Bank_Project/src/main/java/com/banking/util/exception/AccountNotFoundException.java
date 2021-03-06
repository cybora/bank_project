package com.banking.util.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	
	private String message;

	public AccountNotFoundException() {
	}

	public AccountNotFoundException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
