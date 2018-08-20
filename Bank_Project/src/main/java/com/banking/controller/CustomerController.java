package com.banking.controller;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.banking.model.CustomerDetail;
import com.banking.service.CustomerService;
import com.banking.util.error.ErrorDetails;

@RestController
public class CustomerController {

	public static final String INVALID_CUSTOMER_ID = "Customer ID is invalid";
	public static final String CUSTOMER_ID_INTEGER = "Customer ID should be an integer value";
	public static final String CUSTOMER_ID_POSITIVE = "Customer ID should be a positive value";
	
	@Autowired
	private CustomerService customerService ;

	
	@GetMapping("/customerDetails/{id}")
	public CustomerDetail getCustomerDetail(@PathVariable(name = "id" , required = true) Integer id) {
		if (id.compareTo(0) <= 0) { // check if the customer id is positive
			throw new IllegalArgumentException(CUSTOMER_ID_POSITIVE);
		}
		return customerService.getCustomerDetail(id);
	}
	
	/* NumberFormatException handler */
	@ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorDetails> handleException(NumberFormatException e) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), e.getMessage(),
				INVALID_CUSTOMER_ID);
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
