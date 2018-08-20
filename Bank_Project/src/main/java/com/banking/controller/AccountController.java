package com.banking.controller;

import java.math.BigDecimal;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banking.model.Account;
import com.banking.service.AccountService;
import com.banking.util.error.ErrorDetails;

@RestController
public class AccountController {
	
	public static final String INVALID_CUSTOMER_OR_INITIAL_CREDIT = "Customer ID and (or) Initial credit is invalid";
	public static final String CUSTOMER_ID_POSITIVE = "Customer ID should be a positive value";
	public static final String INITIAL_CREDIT_POSITIVE = "Initial credit should be a positive value";
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/createSecondaryAccount/{id}/{initialCredit}")
	public Account createSecondaryAccount(@PathVariable(name = "id", required = true) Integer id,
										  @PathVariable(name = "initialCredit", required = true) BigDecimal initialCredit) {
		if (id.compareTo(0) <= 0) { // check if the customer id is positive
			throw new IllegalArgumentException(CUSTOMER_ID_POSITIVE);
		}
		
		if (initialCredit.compareTo(new BigDecimal(0)) < 0) { // check if the initial credit is is zero or positive
			throw new IllegalArgumentException(INITIAL_CREDIT_POSITIVE);
		}
		return accountService.createSecondaryAccount(id, initialCredit);
	}
	
	
	/* NumberFormatException handler */
	@ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDetails> handleException(NumberFormatException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
				INVALID_CUSTOMER_OR_INITIAL_CREDIT);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
	
	/* IllegalArgumentException handler */
	@ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDetails> handleException(IllegalArgumentException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
				CUSTOMER_ID_POSITIVE);
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
