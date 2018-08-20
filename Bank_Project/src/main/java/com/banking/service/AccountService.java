package com.banking.service;

import static com.banking.util.constant.AccountConstants.INSUFFICIENT_CREDIT;
import static com.banking.util.constant.AccountConstants.NO_PRIMARY_ACCOUNT;
import static com.banking.util.constant.AccountConstants.USER_HAS_SECONDARY_ACCOUNT;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.banking.model.Account;
import com.banking.model.AccountType;
import com.banking.model.Transaction;
import com.banking.repository.AccountRepository;
import com.banking.util.constant.AccountConstants;
import com.banking.util.exception.AccountNotFoundException;
import com.banking.util.exception.AlreadyHasSecondaryAccountException;
import com.banking.util.exception.CustomerNeedsPrimaryAccountException;
import com.banking.util.exception.InsufficientPrimaryAccountCreditException;

@Service
@Transactional
public class AccountService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private AccountRepository accountRepository;
	
	public List<Account> getAccountsByCustomer(Integer customerId) {
		return accountRepository.findAllByOwnerCustomerId(customerId);
	}
	
	public List<Transaction> getAccountsTransaction(Integer accountId) {
		return transactionService.findAllByAccountId(accountId);
	}
	
	public BigDecimal getAccountBalance(Integer accountId) {
		return accountRepository.findBalanceById(accountId);
	}
	
	/* helper service for secondary account creation */
	public Account createAccount(AccountType type,
								  Integer ownerCustomerId,
								  BigDecimal balance) {
		Account account = new Account(type, ownerCustomerId, balance);
		return accountRepository.save(account);
		
	}
	
	public Account createSecondaryAccount(Integer customerId, BigDecimal initialCredit) {
		LOGGER.trace("Checking if secondary account can be created");
		List<Account> accounts = checkSecondaryAccountElligibility(customerId, initialCredit);
		
		Account primaryAccount = accounts.get(0);
		Account secondaryAccount = createAccount(AccountType.SECONDARY, customerId, new BigDecimal(0));
		LOGGER.trace("Secondary account is created");
		
		LOGGER.trace("Transactions are done in case there is an initial credit for the secondary account");
		if (initialCredit.compareTo(new BigDecimal(0)) > 0) {
			transactionService.transactionBetweenAccounts(primaryAccount, secondaryAccount, initialCredit);
		}

		
		return secondaryAccount;
		
	}
	
	public List<Account> checkSecondaryAccountElligibility(Integer customerId, BigDecimal initialCredit) {
		customerService.getCustomer(customerId); // checks for the customers existence
		List<Account> accounts = getAccountsByCustomer(customerId);
		
		if (accounts.isEmpty()) { // check if the customer has any account
			throw new CustomerNeedsPrimaryAccountException(NO_PRIMARY_ACCOUNT);
		}
		
		if (accounts.size() > 1) { // check if the customer has already a secondary account
			throw new AlreadyHasSecondaryAccountException(USER_HAS_SECONDARY_ACCOUNT);
		}
		
		if (initialCredit.compareTo(new BigDecimal(0)) != 0) { // if the initial credit is more than zero, then check if the primary account has sufficient amount to transfer
			Account primaryAccount = accounts.get(0);
			if (primaryAccount.getBalance().compareTo(initialCredit) < 0) {
				throw new InsufficientPrimaryAccountCreditException(INSUFFICIENT_CREDIT);
			}
		}
		
		LOGGER.trace("Secondary account can be created, controls passed");
		
		return accounts;
		
		
	}
	
	public BigDecimal changeBalance(Integer accountId, BigDecimal amount) {
		Account account = new Account();
		try {
			account = accountRepository.findById(accountId) // check if the account exists
					.orElseThrow(() -> new AccountNotFoundException(AccountConstants.ID_NOT_FOUND + accountId));
		
		LOGGER.trace("Transaction is created with account id, other account id and amount");
		account.setBalance(account.getBalance().add(amount));
		account = accountRepository.save(account);

		} catch (AccountNotFoundException e) {
			LOGGER.error("Account not found : {}", e.getMessage());
		}
		
		return account.getBalance();
	}
		

}
