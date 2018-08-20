package com.banking.model;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CustomerDetail {
	
	private String name;
	private String surname;
	private BigDecimal balance;
	private List<Transaction> transactions;
}
