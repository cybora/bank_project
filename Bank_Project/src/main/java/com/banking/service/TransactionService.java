package com.banking.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.model.Account;
import com.banking.model.Transaction;
import com.banking.repository.TransactionRepository;

@Service
public class TransactionService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionRepository transactionRepository;
	
	private Transaction processTransaction(Account account, Account otherParty, BigDecimal amount) {
		LOGGER.trace("Transaction is created with account id {}, other account id {} and amount {}", account.getId(), otherParty.getId(), amount);
		Transaction transaction = new Transaction(account, otherParty.getId(), amount);
		transaction = transactionRepository.save(transaction);
		
		/* if the transaction list is null then create one */
		if (Objects.isNull(account.getTransactionList())) {
			account.setTransactionList(new ArrayList<Transaction>());
		}
		
		account.getTransactionList().add(transaction);
		accountService.changeBalance(account.getId(), amount);
		
		return transaction;
	}
	
	
	
	public BigDecimal transactionBetweenAccounts(Account account, Account otherParty, BigDecimal amount) {
		LOGGER.trace("Transactions have been started");
		processTransaction(account, otherParty, amount.negate()); // sender transaction , balance decreased
		processTransaction(otherParty, account, amount); // receiver transaction, balance increased
		LOGGER.trace("Transactions are completed and balances are updated");
		return otherParty.getBalance();
	}
	
	public List<Transaction> findAllByAccountId(Integer id) {
		return transactionRepository.findAllByAccountId(id);
	}
	
	

}
