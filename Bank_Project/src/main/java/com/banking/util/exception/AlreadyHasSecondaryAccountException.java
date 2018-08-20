package com.banking.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AlreadyHasSecondaryAccountException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String message;

	public AlreadyHasSecondaryAccountException() {
	}

	public AlreadyHasSecondaryAccountException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
