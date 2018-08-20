package com.banking.util.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InsufficientPrimaryAccountCreditException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int errorCode;
	private String errorMessage;

	public InsufficientPrimaryAccountCreditException(Throwable throwable) {
		super(throwable);
	}

	public InsufficientPrimaryAccountCreditException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public InsufficientPrimaryAccountCreditException(String message) {
		super(message);
	}

	public InsufficientPrimaryAccountCreditException(String message, int errorCode) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = message;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		return this.errorCode + " : " + this.getErrorMessage();
	}

}
