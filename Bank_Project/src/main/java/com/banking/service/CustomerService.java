package com.banking.service;

import static com.banking.util.constant.CustomerConstants.ID_NOT_FOUND;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.model.Account;
import com.banking.model.Customer;
import com.banking.model.CustomerDetail;
import com.banking.model.Transaction;
import com.banking.repository.CustomerRepository;
import com.banking.util.exception.CustomerNotFoundException;

@Service
public class CustomerService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountService accountService;

	public CustomerDetail getCustomerDetail(Integer id) {
		LOGGER.trace("Compiling the customer info, balance and transactions");
		/* compiling customer, balance and the transaction info for the detailed info */
		Customer customer = getCustomer(id);
		BigDecimal balance = getCustomerBalance(id);
		List<Transaction> transactions = getCustomerTransactions(id);
		LOGGER.trace("Compiling customer details done.");
		return new CustomerDetail(customer.getName(), customer.getSurname(), balance, transactions);

	}

	public Customer getCustomer(Integer id) {
		return customerRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException(ID_NOT_FOUND + id));
	}

	public BigDecimal getCustomerBalance(Integer id) {
		return accountService.getAccountsByCustomer(id).stream()
													   .map(Account::getBalance)
													   .reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public List<Transaction> getCustomerTransactions(Integer id) {
		List<Account> accounts = accountService.getAccountsByCustomer(id);
		List<Transaction> transactions = new ArrayList<>();
		accounts.stream().map(Account::getId)
				.forEach(accountId -> transactions.addAll(accountService.getAccountsTransaction(accountId)));

		return transactions;
	}

}
